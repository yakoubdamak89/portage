# Portage Pro – CI/CD & Déploiement automatique

> Ce document complète [`DEPLOY.md`](DEPLOY.md) en couvrant uniquement la partie
> CI/CD : pipeline GitHub Actions, secrets à configurer, déclenchement manuel et
> diagnostic des échecs.

---

## Table des matières

1. [Vue d'ensemble](#1-vue-densemble)
2. [Secrets à configurer](#2-secrets-à-configurer)
3. [Fonctionnement après merge](#3-fonctionnement-après-merge)
4. [Déclencher manuellement un déploiement](#4-déclencher-manuellement-un-déploiement)
5. [Lire les logs et diagnostiquer un échec](#5-lire-les-logs-et-diagnostiquer-un-échec)
6. [Prérequis côté VPS](#6-prérequis-côté-vps)

---

## 1. Vue d'ensemble

```
PR mergée sur main
       │
       ▼
┌──────────────────────────────────────────────────────┐
│  GitHub Actions  .github/workflows/deploy.yml        │
│                                                      │
│  Job 1 – CI : Build & Verify                         │
│    ├─ Maven build (backend Java 17)                  │
│    └─ Angular build production (frontend Node 20)    │
│                                                      │
│  Job 2 – CD : Deploy to VPS   (si Job 1 OK)         │
│    └─ SSH → scripts/deploy.sh                        │
│         ├─ git pull origin/main                      │
│         ├─ docker compose up -d --build              │
│         ├─ attente du healthcheck backend            │
│         └─ docker image prune -f                     │
└──────────────────────────────────────────────────────┘
```

Le pipeline se compose de deux jobs séquentiels :

| Job | Quand | Rôle |
|-----|-------|------|
| **ci** | À chaque push sur `main` | Compile et vérifie le projet. Bloque le déploiement si le build échoue. |
| **deploy** | Uniquement si `ci` passe | Se connecte au VPS en SSH et exécute `scripts/deploy.sh`. |

---

## 2. Secrets à configurer

Rendez-vous dans **GitHub → votre dépôt → Settings → Secrets and variables →
Actions → New repository secret** et ajoutez les secrets suivants :

### Secrets d'infrastructure (connexion SSH au VPS)

| Nom du secret | Description | Exemple |
|---------------|-------------|---------|
| `VPS_HOST` | Adresse IP ou nom de domaine du VPS | `203.0.113.10` ou `vps.mondomaine.fr` |
| `VPS_PORT` | Port SSH du VPS | `22` (valeur par défaut) |
| `VPS_USER` | Utilisateur SSH sur le VPS | `deploy` ou `ubuntu` |
| `VPS_SSH_KEY` | Clé privée SSH (au format PEM complet, **avec** les lignes `-----BEGIN…-----`) | voir ci-dessous |
| `VPS_DEPLOY_PATH` | Chemin absolu du répertoire de déploiement sur le VPS | `/home/deploy/portage` |

### Secrets applicatifs (variables d'environnement)

Ces secrets sont **automatiquement écrits dans le fichier `.env`** sur le VPS à chaque
déploiement. Ils remplacent la configuration manuelle décrite dans `DEPLOY.md § 4`.

| Nom du secret | Obligatoire | Description | Exemple |
|---------------|-------------|-------------|---------|
| `POSTGRES_PASSWORD` | ✅ | Mot de passe PostgreSQL | `M0tD3P@ss3Tr3sF0rt` |
| `JWT_SECRET` | ✅ | Clé secrète JWT (min 32 caractères) | `openssl rand -base64 48` |
| `JWT_EXPIRATION` | non | Durée JWT en ms | `86400000` (24 h, valeur par défaut) |
| `POSTGRES_DB` | non | Nom de la base de données | `portagedb` (valeur par défaut) |
| `POSTGRES_USER` | non | Utilisateur PostgreSQL | `portage` (valeur par défaut) |
| `MAIL_HOST` | non | Serveur SMTP | `localhost` (valeur par défaut) |
| `MAIL_PORT` | non | Port SMTP | `1025` (valeur par défaut) |
| `MAIL_USERNAME` | non | Utilisateur SMTP | *(vide)* |
| `MAIL_PASSWORD` | non | Mot de passe SMTP | *(vide)* |
| `MAIL_SMTP_AUTH` | non | Authentification SMTP | `false` (valeur par défaut) |
| `MAIL_SMTP_STARTTLS` | non | STARTTLS SMTP | `false` (valeur par défaut) |
| `FRONTEND_PORT` | non | Port exposé par Nginx | `80` (valeur par défaut) |

> **Important** : si `POSTGRES_PASSWORD` ou `JWT_SECRET` ne sont pas définis dans les
> secrets GitHub, le pipeline **ne modifie pas** le `.env` existant sur le VPS. Si le
> fichier n'existe pas encore, `deploy.sh` s'arrête avec une erreur explicite.

### Générer une paire de clés SSH dédiée au déploiement

```bash
# Générer la paire (sans passphrase pour l'automatisation)
ssh-keygen -t ed25519 -C "github-actions-deploy" -f ~/.ssh/portage_deploy -N ""

# Afficher la clé publique → la copier dans ~/.ssh/authorized_keys sur le VPS
cat ~/.ssh/portage_deploy.pub

# Afficher la clé privée → la coller dans le secret GitHub VPS_SSH_KEY
cat ~/.ssh/portage_deploy
```

Sur le VPS, ajouter la clé publique à l'utilisateur de déploiement :

```bash
echo "<clé_publique>" >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
```

---

## 3. Fonctionnement après merge

1. Un développeur ouvre une **Pull Request** vers `main` et la fait relire.
2. Après approbation, la PR est **mergée**.
3. GitHub Actions détecte le push sur `main` et démarre le workflow automatiquement.
4. **Job CI** : compile le backend Maven et le frontend Angular. Si une étape échoue, le workflow s'arrête et aucun déploiement n'a lieu.
5. **Job CD** : se connecte au VPS en SSH et exécute `scripts/deploy.sh` :
   - récupère le dernier commit (`git reset --hard origin/main`)
   - reconstruit les images Docker (`docker compose up -d --build`)
   - attend que le backend réponde au healthcheck (`/actuator/health`)
   - supprime les images inutilisées (`docker image prune -f`)
6. Le statut final (✅ succès ou ❌ échec) est visible dans l'onglet **Actions** du dépôt.

> **Interruptions de service** : le déploiement remplace les conteneurs un par un.
> PostgreSQL n'est jamais recréé (volume persistant). Le backend et le frontend
> peuvent être indisponibles pendant quelques secondes le temps du rebuild.

---

## 4. Déclencher manuellement un déploiement

### Via l'interface GitHub (workflow_dispatch)

1. Aller sur **GitHub → Actions → CI/CD – Build & Deploy**.
2. Cliquer sur **Run workflow** (menu déroulant à droite).
3. Sélectionner la branche `main` puis **Run workflow**.

### Via la ligne de commande (gh CLI)

```bash
gh workflow run deploy.yml --ref main
```

### Directement sur le VPS

En cas d'urgence (ex. : GitHub injoignable), se connecter en SSH et lancer :

```bash
cd ~/portage
bash scripts/deploy.sh
```

---

## 5. Lire les logs et diagnostiquer un échec

### Logs GitHub Actions

1. Aller sur **GitHub → Actions → CI/CD – Build & Deploy**.
2. Cliquer sur le run concerné.
3. Développer les étapes du job pour voir la sortie complète.

Les erreurs les plus courantes :

| Symptôme | Cause probable | Solution |
|----------|----------------|----------|
| Job **ci** échoue à `mvn package` | Erreur de compilation Java | Corriger le code et pousser un nouveau commit |
| Job **ci** échoue à `npm run build` | Erreur TypeScript / Angular | Corriger le code et pousser un nouveau commit |
| Job **deploy** échoue à la connexion SSH | Secret incorrect ou VPS inaccessible | Vérifier `VPS_HOST`, `VPS_PORT`, `VPS_USER`, `VPS_SSH_KEY` |
| `deploy.sh` échoue avec `POSTGRES_PASSWORD is missing a value` | Le fichier `.env` est absent ou contient encore les valeurs d'exemple | Vérifier que les secrets `POSTGRES_PASSWORD` et `JWT_SECRET` sont bien définis dans GitHub → Settings → Secrets |
| `deploy.sh` échoue sur `docker compose up` | Erreur de build Docker sur le VPS | Voir les logs Docker ci-dessous |
| Healthcheck backend timeout | Application ne démarre pas | Voir les logs backend ci-dessous |

### Logs Docker sur le VPS

Se connecter au VPS et exécuter :

```bash
cd ~/portage

# État de tous les conteneurs
docker compose ps

# Logs complets (toutes les services, temps réel)
docker compose logs -f

# Logs des 100 dernières lignes du backend uniquement
docker compose logs --tail=100 backend

# Logs du frontend (Nginx)
docker compose logs --tail=50 frontend

# Logs PostgreSQL
docker compose logs --tail=50 postgres
```

### Redémarrer un service individuel sans rebuild

```bash
cd ~/portage
docker compose restart backend
docker compose restart frontend
```

### Vérifier le healthcheck backend manuellement

```bash
curl -fsS http://localhost:8080/actuator/health
```

Réponse attendue : `{"status":"UP"}` (avec potentiellement plus de détails).

### Inspecter les variables d'environnement d'un conteneur

```bash
docker inspect portage-backend | grep -A 30 '"Env"'
```

---

## 6. Prérequis côté VPS

Avant le premier déploiement automatique, s'assurer que le VPS est correctement préparé :

1. **Docker & Docker Compose** installés (voir [`DEPLOY.md § 2`](DEPLOY.md#2-installation-docker--docker-compose)).
2. **Dépôt cloné** dans le répertoire home de l'utilisateur SSH :
   ```bash
   git clone https://github.com/IngeniousDevelopersINDEV/portage.git ~/portage
   ```
3. **Secrets GitHub configurés** (voir [§ 2](#2-secrets-à-configurer)) : le pipeline
   crée/met à jour automatiquement `~/portage/.env` à chaque déploiement.
4. **Clé publique SSH** de déploiement ajoutée à `~/.ssh/authorized_keys` (voir [§ 2](#2-secrets-à-configurer)).

Après ces étapes, chaque merge sur `main` déclenche automatiquement le redéploiement.
