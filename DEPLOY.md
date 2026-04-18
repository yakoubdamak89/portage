# Portage Pro – Guide de déploiement production

> **Cible** : VPS Ubuntu 24.04 dédié – Docker Compose – PostgreSQL – Nginx

---

## Table des matières

1. [Prérequis](#1-prérequis)
2. [Installation Docker & Docker Compose](#2-installation-docker--docker-compose)
3. [Cloner le dépôt](#3-cloner-le-dépôt)
4. [Configurer les variables d'environnement](#4-configurer-les-variables-denvironnement)
5. [Build et lancement](#5-build-et-lancement)
6. [Accès à l'application](#6-accès-à-lapplication)
7. [Configuration d'un domaine ou sous-domaine](#7-configuration-dun-domaine-ou-sous-domaine)
8. [Activer HTTPS avec Let's Encrypt](#8-activer-https-avec-lets-encrypt)
9. [Commandes de maintenance](#9-commandes-de-maintenance)
10. [Architecture](#10-architecture)
11. [Hypothèses et notes](#11-hypothèses-et-notes)

---

## 1. Prérequis

| Élément | Version minimale |
|---------|-----------------|
| OS | Ubuntu 24.04 LTS |
| RAM | 2 Go (4 Go recommandés) |
| Disque | 20 Go libres |
| Docker | 24+ |
| Docker Compose | v2.20+ (plugin intégré à Docker) |
| Port 80 ouvert | Pour HTTP / Let's Encrypt |
| Port 443 ouvert | Pour HTTPS |

---

## 2. Installation Docker & Docker Compose

```bash
# Mettre à jour le système
sudo apt-get update && sudo apt-get upgrade -y

# Installer les dépendances
sudo apt-get install -y ca-certificates curl gnupg

# Ajouter la clé GPG officielle Docker
sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | \
  sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

# Ajouter le dépôt Docker
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] \
  https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Installer Docker Engine + plugin Compose
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Permettre à l'utilisateur courant d'utiliser Docker sans sudo
sudo usermod -aG docker $USER
newgrp docker

# Vérification
docker --version
docker compose version
```

---

## 3. Cloner le dépôt

```bash
git clone https://github.com/IngeniousDevelopersINDEV/portage.git
cd portage
```

---

## 4. Configurer les variables d'environnement

```bash
# Copier le fichier d'exemple
cp .env.example .env

# Éditer les valeurs
nano .env
```

### Variables requises

| Variable | Description | Exemple |
|----------|-------------|---------|
| `POSTGRES_PASSWORD` | Mot de passe PostgreSQL **(obligatoire)** | `M0tD3P@ss3Tr3sF0rt` |
| `JWT_SECRET` | Clé secrète JWT, min 32 caractères **(obligatoire)** | voir ci-dessous |

### Générer une clé JWT sécurisée

```bash
openssl rand -base64 48
```

Copier la sortie dans le champ `JWT_SECRET` du fichier `.env`.

### Variables optionnelles

| Variable | Valeur par défaut | Description |
|----------|-------------------|-------------|
| `POSTGRES_DB` | `portagedb` | Nom de la base de données |
| `POSTGRES_USER` | `portage` | Utilisateur PostgreSQL |
| `JWT_EXPIRATION` | `86400000` | Durée JWT en ms (24 h) |
| `MAIL_HOST` | `localhost` | Serveur SMTP |
| `MAIL_PORT` | `1025` | Port SMTP |
| `MAIL_USERNAME` | *(vide)* | Utilisateur SMTP |
| `MAIL_PASSWORD` | *(vide)* | Mot de passe SMTP |
| `MAIL_SMTP_AUTH` | `false` | Authentification SMTP |
| `MAIL_SMTP_STARTTLS` | `false` | STARTTLS SMTP |
| `FRONTEND_PORT` | `80` | Port exposé par Nginx sur l'hôte |

> **Note** : La fonctionnalité d'envoi d'e-mails est définie dans le code mais non critique au démarrage. Vous pouvez laisser les variables MAIL par défaut si vous ne l'utilisez pas.

---

## 5. Build et lancement

```bash
# Premier démarrage (build des images + lancement)
docker compose up -d --build

# Vérifier que tous les conteneurs sont UP
docker compose ps
```

Le build peut prendre **3 à 8 minutes** selon la connexion et les ressources du VPS (téléchargement des dépendances Maven et npm).

### 5.1 Redémarrage automatique avec systemd (recommandé)

Pour que les conteneurs (postgres, backend, frontend) **démarrent automatiquement au boot** du VPS et soient gérés de manière centralisée, installez le service systemd inclus dans le projet :

```bash
sudo bash scripts/setup-systemd.sh /chemin/vers/portage
# Exemple : sudo bash scripts/setup-systemd.sh /home/deploy/portage
```

Après l'installation, vous n'avez **plus jamais besoin de lancer `docker compose` manuellement**. Le service gère tout :

```bash
# Vérifier l'état
systemctl status portage

# Redémarrer tous les conteneurs
systemctl restart portage

# Arrêter tous les conteneurs
systemctl stop portage

# Démarrer tous les conteneurs
systemctl start portage

# Voir les logs du service
journalctl -u portage -f
```

> **Note** : Quand le service systemd est installé, le script `deploy.sh` (utilisé par la CI/CD) l'utilise automatiquement pour relancer les conteneurs après chaque déploiement. Plus besoin d'intervention manuelle.

### Vérifier les logs en temps réel

```bash
# Tous les services
docker compose logs -f

# Backend uniquement
docker compose logs -f backend

# Frontend / Nginx uniquement
docker compose logs -f frontend

# PostgreSQL uniquement
docker compose logs -f postgres
```

L'application est prête quand vous voyez dans les logs backend :

```
Started PortageApplication in X.XXX seconds
```

---

## 6. Accès à l'application

| URL | Description |
|-----|-------------|
| `http://<IP_DU_VPS>` | Application Angular |
| `http://<IP_DU_VPS>/api` | API REST Spring Boot |
| `http://<IP_DU_VPS>/swagger-ui.html` | Documentation Swagger |

### Comptes de démonstration (créés automatiquement au premier démarrage)

| Email | Mot de passe | Rôle |
|-------|-------------|------|
| `admin@portage.fr` | `Admin123!` | Admin |
| `gestionnaire@portage.fr` | `Gestionnaire123!` | Gestionnaire |
| `consultant1@portage.fr` | `Consultant123!` | Salarié porté |
| `consultant2@portage.fr` | `Consultant123!` | Salarié porté |

> **Important** : Changez ces mots de passe dès la première connexion en production.

---

## 7. Configuration d'un domaine ou sous-domaine

### 7.1 Pointer le DNS vers votre VPS

Chez votre registrar/hébergeur DNS, créez un enregistrement **A** :

```
Type : A
Nom  : @ (ou sous-domaine, ex: app)
Valeur: <IP_DU_VPS>
TTL  : 3600
```

Exemples :
- `portage.monentreprise.fr` → IP du VPS
- `app.portage.fr` → IP du VPS

### 7.2 Attendre la propagation DNS

```bash
# Vérifier la propagation
dig +short portage.monentreprise.fr
```

---

## 8. Activer HTTPS avec Let's Encrypt

> **Prérequis** : Le domaine doit pointer vers votre VPS et le port 80 doit être accessible depuis internet.

### 8.1 Installer Certbot

```bash
sudo apt-get install -y certbot python3-certbot-nginx
```

### 8.2 Arrêter temporairement le conteneur frontend (qui utilise le port 80)

```bash
docker compose stop frontend
```

### 8.3 Obtenir le certificat

```bash
sudo certbot certonly --standalone -d portage.monentreprise.fr
```

Les certificats sont stockés dans `/etc/letsencrypt/live/portage.monentreprise.fr/`.

### 8.4 Configurer Nginx sur l'hôte comme reverse proxy HTTPS

Plutôt que d'exposer directement le port 80, on met Nginx sur l'hôte en frontal HTTPS et on fait écouter Docker sur un port local (ex: 8090).

**Modifier `.env` :**

```env
FRONTEND_PORT=127.0.0.1:8090
```

**Redémarrer :**

```bash
docker compose up -d frontend
```

**Installer et configurer Nginx sur l'hôte :**

```bash
sudo apt-get install -y nginx
sudo nano /etc/nginx/sites-available/portage
```

Contenu du fichier :

```nginx
server {
    listen 80;
    server_name portage.monentreprise.fr;
    return 301 https://$host$request_uri;
}

server {
    listen 443 ssl;
    server_name portage.monentreprise.fr;

    ssl_certificate     /etc/letsencrypt/live/portage.monentreprise.fr/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/portage.monentreprise.fr/privkey.pem;
    ssl_protocols       TLSv1.2 TLSv1.3;
    ssl_ciphers         HIGH:!aNULL:!MD5;

    location / {
        proxy_pass         http://127.0.0.1:8090;
        proxy_set_header   Host $host;
        proxy_set_header   X-Real-IP $remote_addr;
        proxy_set_header   X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header   X-Forwarded-Proto $scheme;
    }
}
```

```bash
sudo ln -s /etc/nginx/sites-available/portage /etc/nginx/sites-enabled/
sudo nginx -t && sudo systemctl reload nginx
```

### 8.5 Renouvellement automatique

Let's Encrypt expire tous les 90 jours. Le renouvellement est automatique via la cron de Certbot :

```bash
# Vérifier que la cron est en place
sudo systemctl status certbot.timer

# Tester le renouvellement
sudo certbot renew --dry-run
```

---

## 9. Commandes de maintenance

### Démarrer / Arrêter / Redémarrer

```bash
# Démarrer tous les services
docker compose up -d

# Arrêter tous les services (données conservées)
docker compose down

# Redémarrer un service
docker compose restart backend
docker compose restart frontend
docker compose restart postgres
```

### Mettre à jour l'application

```bash
# Récupérer les dernières modifications
git pull

# Rebuilder et redémarrer
docker compose up -d --build
```

### Logs

```bash
# Logs en continu de tous les services
docker compose logs -f

# Logs des 100 dernières lignes du backend
docker compose logs --tail=100 backend

# Logs avec horodatage
docker compose logs -t backend
```

### Accéder à la base de données

```bash
# Shell PostgreSQL interactif
docker compose exec postgres psql -U portage -d portagedb

# Quelques commandes utiles dans psql
\dt          -- lister les tables
\du          -- lister les utilisateurs
\q           -- quitter
```

### Sauvegarder la base de données

```bash
# Dump complet
docker compose exec postgres pg_dump -U portage portagedb > backup_$(date +%Y%m%d_%H%M%S).sql

# Restaurer depuis un dump
cat backup_20240101_120000.sql | docker compose exec -T postgres psql -U portage -d portagedb
```

### Nettoyer les ressources Docker inutilisées

```bash
# Images non utilisées
docker image prune -f

# Tout nettoyer (attention : supprime les données non persistées)
docker system prune -f
```

### Statut et ressources

```bash
# État des conteneurs
docker compose ps

# Utilisation des ressources (CPU, RAM)
docker stats

# Espace disque des volumes
docker system df
```

---

## 10. Architecture

```
Internet
    │
    ▼
[ Nginx hôte (443 HTTPS) ]   ← optionnel pour HTTPS
    │
    ▼
[ Conteneur frontend :80 ]   (Nginx Alpine – sert Angular + proxy /api)
    │                │
    │ /api/*         │ /  (fichiers statiques Angular)
    ▼                │
[ Conteneur backend :8080 ]  (Spring Boot Java 17 – SPRING_PROFILES_ACTIVE=prod)
    │
    ▼
[ Conteneur postgres :5432 ] (PostgreSQL 16 – volume persistant)
```

**Réseau Docker interne** : `portage-network` (bridge)
- `postgres` : non exposé publiquement
- `backend` : non exposé publiquement (accessible uniquement par `frontend` via le réseau Docker)
- `frontend` : exposé sur le port défini par `FRONTEND_PORT` (80 par défaut)

**Volume persistant** : `postgres_data` – les données PostgreSQL survivent aux redémarrages et mises à jour.

### Migration de base de données

Le backend utilise `spring.jpa.hibernate.ddl-auto: update` en production. Cela signifie que Hibernate :
- Crée automatiquement les tables manquantes au démarrage
- Ajoute les colonnes manquantes
- **Ne supprime pas** les colonnes ou tables existantes

> **Remarque** : Pour un projet en production mûr, il est recommandé d'introduire Flyway ou Liquibase pour des migrations versionnées et reproductibles. En l'état actuel du projet, `ddl-auto: update` est suffisant.

---

## 11. Hypothèses et notes

| Sujet | Hypothèse / Décision |
|-------|---------------------|
| **H2 → PostgreSQL** | Le driver PostgreSQL a été ajouté au `pom.xml`. Le profil `prod` est activé via `SPRING_PROFILES_ACTIVE=prod`, qui utilise `application-prod.yml`. |
| **Migrations DB** | Aucun outil de migration (Flyway/Liquibase) n'est présent. `ddl-auto: update` est utilisé. Les tables sont créées automatiquement au premier démarrage. |
| **Données de démo** | Le `DataInitializer` crée automatiquement des comptes de démonstration au premier démarrage (si la base est vide). |
| **Envoi d'e-mails** | La dépendance `spring-boot-mail` est présente mais aucun flux critique ne dépend de l'envoi. Laisser les variables MAIL par défaut si non utilisé. |
| **Frontend API URL** | Le fichier `environment.prod.ts` utilise déjà `/api` (URL relative), ce qui permet à Nginx de proxifier correctement sans configuration Angular supplémentaire. |
| **HTTPS** | Non inclus dans Docker Compose par défaut. La section 8 du guide détaille la configuration avec Certbot + Nginx hôte. |
| **Sauvegardes** | Non automatisées. Voir la section 9 pour les commandes manuelles. Configurez un cron job pour les sauvegardes automatiques en production. |
