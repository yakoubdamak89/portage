# Portage Pro

Application web de gestion du portage salarial, composée d'un backend Spring Boot et d'un frontend Angular 19.

## Stack technique

| Couche | Technologie |
|--------|------------|
| Frontend | Angular 19.2, Angular Material 19.2 |
| Backend | Spring Boot (Java) |
| Base de données | PostgreSQL |
| Authentification | JWT (Bearer token) |

## Modules frontend

### Module Manager (`/manager`)
Accessible aux rôles `ADMIN` et `GESTIONNAIRE`.

- **Tableau de bord** – KPIs, factures récentes, missions se terminant bientôt
- **Consultants** – liste et détail des consultants portés
- **Clients** – liste et détail des clients
- **Missions** – suivi des missions actives
- **Comptes rendus d'activité** – validation des CRA soumis par les consultants
- **Factures** – liste et détail des factures
- **Paiements** – suivi des paiements reçus
- **Gestion paie** – génération et suivi des bulletins de paie

### Module Consultant (`/consultant`)
Accessible aux rôles `SALARIE_PORTE` et `ADMIN`.

- **Tableau de bord** – mission en cours, jours déclarés, revenu mensuel, solde épargne
- **Comptes rendus d'activité** – saisie et soumission des CRA mensuels
- **Notes de frais** – saisie et soumission des notes de frais
- **Bulletins de paie** – consultation et téléchargement des bulletins
- **Documents** – accès aux documents contractuels
- **Mon compte épargne** – solde, historique des mouvements, simulateur de revenu

## Sécurité

La montée de version Angular 17 → 19 corrige la vulnérabilité XSRF :
- `HttpClientModule` remplacé par `provideHttpClient(withInterceptorsFromDi(), withXsrfConfiguration({ cookieName: 'XSRF-TOKEN', headerName: 'X-XSRF-TOKEN' }))` dans `AppModule`.

## Démarrage rapide

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend
cd frontend
npm install
npm start
```

L'application frontend est disponible sur `http://localhost:4200`.
L'API backend écoute sur `http://localhost:8080/api`.
