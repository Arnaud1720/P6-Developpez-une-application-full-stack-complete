# P6‑Full‑Stack‑reseau‑dev – README

Guide clair et pas‑à‑pas pour installer et lancer l’application **full‑stack MVP** (Back Java + Front Angular) avec une base de données SGBD via **Docker**.

---

##  Sommaire

* [Aperçu](#aperçu)
* [Prérequis](#prérequis)
* [Installation rapide (TL;DR)](#installation-rapide-tldr)
* [Cloner le projet](#cloner-le-projet)
* [Base de données (Docker)](#base-de-données-docker)

    * [docker-compose.yml](#docker-composeyml)
    * [Démarrage / arrêt](#démarrage--arrêt)
    * [Vérifier la connexion](#vérifier-la-connexion)
* [Backend (Java + Maven)](#backend-java--maven)

    * [Configuration](#configuration)
    * [Lancement en dev](#lancement-en-dev)
    * [Build & exécution (jar)](#build--exécution-jar)
* [Frontend (Angular)](#frontend-angular)

    * [Lancement en dev](#lancement-en-dev-1)
    * [Build de production](#build-de-production)
* [Comptes de démo (optionnel)](#comptes-de-démo-optionnel)
* [Scripts utiles (optionnel)](#scripts-utiles-optionnel)
* [Dépannage](#dépannage)
* [Annexes](#annexes)

---

## Aperçu

* **Backend** : Java (recommandé **JDK 17 LTS**), **Maven**, API REST (ex: Spring Boot)
* **Frontend** : **Angular 14.1.3** (CLI), UI optionnelle : **@angular/material**
* **SGBD** : **PostgreSQL** (via Docker)

> ⚠️ Adaptez les noms de modules/dossiers (ex: `backend/`, `front/`) aux vôtres si différents.

---

## Prérequis

* **Git** ≥ 2.3
* **Docker** ≥ 20.x et **Docker Compose** (V2)
* **Java JDK** : **17** (LTS)
* **Maven** ≥ 3.8
* **Node.js** LTS (ex: 16/18) + **npm**
* **Angular CLI** global : `npm i -g @angular/cli@14.1.3`

Vérifier les versions :

```bash
java -version
mvn -v
node -v
npm -v
docker -v
docker compose version
ng version
```

---

## Installation rapide (TL;DR)

```bash
# 1) Cloner
git clone <URL_DU_DEPOT>
cd P6-Full-Stack-reseau-dev

# 2) Démarrer la base (Docker)
cp bdd/.env.example bdd/.env              # si présent
docker compose -f bdd/docker-compose.yml up -d

# 3) Backend
cd backend
cp src/main/resources/application-example.yml src/main/resources/application.yml
mvn spring-boot:run
# API disponible sur http://localhost:8080

# 4) Frontend
cd ../front
npm install
ng serve -o
# Front sur http://localhost:4200
```

---

## Cloner le projet

```bash
git clone <URL_DU_DEPOT>
cd P6-Full-Stack-reseau-dev
```

---

## Base de données (Docker)

### `docker-compose.yml`

Placez ce fichier (ou adaptez le vôtre) dans `bdd/docker-compose.yml` :
### Démarrage / arrêt

```bash
# Depuis le dossier bdd/
docker compose up -d            # démarrer en arrière-plan
docker compose ps               # vérifier les conteneurs
docker compose logs -f db       # suivre les logs de Postgres
docker compose stop             # arrêter
docker compose down             # arrêter + supprimer (garde le volume)
```

### Vérifier la connexion

* **Host** : `localhost`
* **Port** : `5432`

---

## Backend (Java + Maven)

### Configuration

Dans `backend/src/main/resources/application.yml` (ou `.properties`) :

```yaml
server:
  port: 8080
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/mdd_mysql
    username: mdd_user
    password: mdd_password
  jpa:
    hibernate:
      ddl-auto: update   # ou validate / none en prod
    properties:
      hibernate:
        format_sql: true
  jackson:
    serialization:
      INDENT_OUTPUT: true

# CORS simple pour le front local
cors:
  allowed-origins: "http://localhost:4200"
```

> Si vous utilisez **Flyway**/**Liquibase**, mettez `ddl-auto: validate` et laissez les migrations gérer le schéma.

### Lancement en dev

```bash
cd backend
mvn clean install
mvn spring-boot:run
# ou
mvn -DskipTests spring-boot:run
```

* API disponible sur : `http://localhost:8080`
* (Optionnel) Swagger/OpenAPI : `http://localhost:8080/swagger-ui.html` ou `/swagger-ui/index.html`

### Build & exécution (jar)

```bash
mvn -DskipTests package
java -jar target/*.jar
```

---

## Frontend (Angular)

### Lancement en dev

```bash
cd front
npm install
# Angular CLI installé globalement si besoin : npm i -g @angular/cli@14.1.3
ng serve --open
```

* Application : `http://localhost:4200`
* L’UI **@angular/material** est déjà configurée (voir `home.component.*`).

> Si l’API est sur un autre port/hôte, configurez l’URL dans vos **environnements** Angular (ex: `src/environments/environment.ts`).

### Build de production

```bash
ng build --configuration production
# artefacts dans front/dist/
```


### Makefile

```makefile
.PHONY: up down logs api web
up:
	cd infra && docker compose up -d

down:
	cd infra && docker compose down

logs:
	cd infra && docker compose logs -f

api:
	cd backend && mvn spring-boot:run

web:
	cd front && ng serve -o
```

### PowerShell (Windows)

```powershell
# start.ps1
cd infra; docker compose up -d; cd ..
cd backend; mvn spring-boot:run; # Ctrl+C pour arrêter
cd ..\front; npm install; ng serve -o
```

---

## Dépannage

* **Port 5432 déjà utilisé** : changez le mapping dans `docker-compose.yml` (ex: `5433:5432`).
* **Connexion refusée à la DB** : attendez que le healthcheck soit `healthy` ou vérifiez `docker compose logs -f db`.
* **Erreur CORS depuis Angular** : assurez‑vous d’autoriser `http://localhost:4200` côté backend.
* **Version Java non supportée** : utilisez **JDK 17**. Vérifiez `JAVA_HOME`.
* **ng non trouvé** : `npm i -g @angular/cli@14.1.3`.
* **Problèmes de dépendances npm** : supprimez `node_modules/` et `package-lock.json`, puis `npm install`.
* **Migrations DB** : si vous utilisez Flyway, mettez vos scripts dans `backend/src/main/resources/db/migration`.

---

## Annexes

### Variables d’environnement recommandées (exemple)

```bash
# backend/.env.local (si vous en faites usage)
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/p6
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SERVER_PORT=8080
CORS_ALLOWED_ORIGINS=http://localhost:4200
```

MIT (ou autre — à compléter selon votre projet).
