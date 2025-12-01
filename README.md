# Eureka Server

## 1. Rôle du projet

Ce projet fournit le **registre de services** de l’architecture microservices via **Spring Cloud Netflix Eureka**.

Tous les microservices (SERVICE-CLIENT, SERVICE-VOITURE, GateWay) s’y **enregistrent** et le consultent pour faire de la **découverte de services** et du **load balancing**.

---

## 2. Contexte et architecture 

- Architecture microservices basée sur Spring Boot / Spring Cloud.
- Chaque service a son propre domaine fonctionnel et sa propre base (H2 en mémoire ici).
- Eureka joue le rôle de **registre dynamique** :
  - les services envoient des **heartbeats**,
  - Eureka maintient la liste des instances actives,
  - les clients (Gateway, OpenFeign) utilisent le nom logique du service (`spring.application.name`).

Topologie du lab :

- **Eureka Server** : port `8761`
- **SERVICE-CLIENT** : port `8088`
- **SERVICE-VOITURE** : port `8089`
- **GateWay** : port `8888`

---

## 3. Prérequis

- Java 21+ ou 25 (selon ton JDK)
- Maven 3.x
- IDE (IntelliJ IDEA, Eclipse, VS Code…) ou ligne de commande

---

## 4. Configuration importante

Dans `application.properties` (ou `application.yml`) :

```properties
spring.application.name=EUREKA-SERVER
server.port=8761
```

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false



---

---

## Client


# SERVICE-CLIENT

## 1. Rôle du projet

`SERVICE-CLIENT` est un microservice Spring Boot qui expose un **CRUD sur les clients** (id, nom, âge, etc.), avec une base **H2 en mémoire**.

Il est enregistré dans Eureka sous le nom logique :

```properties
spring.application.name=SERVICE-CLIENT
```


##  service-voiture


# SERVICE-VOITURE

## 1. Rôle du projet

`SERVICE-VOITURE` est un microservice Spring Boot qui gère des **voitures** et les associe à des **clients**.

Spécificités :

- Base H2 en mémoire pour les voitures.
- Enrichissement des voitures avec les données client via **OpenFeign** vers `SERVICE-CLIENT`.
- Enregistrement automatique dans Eureka pour être accessible via la Gateway.

---

## 2. Contexte dans l’architecture

- Nom logique du service :  

```properties
spring.application.name=SERVICE-VOITURE
```



---

## GateWay


# API Gateway (Spring Cloud Gateway)

## 1. Rôle du projet

Le projet **GateWay** est le **point d’entrée unique** de l’architecture.  

Il utilise **Spring Cloud Gateway** pour :

- router les requêtes vers `SERVICE-CLIENT` et `SERVICE-VOITURE`,
- utiliser la **découverte de services** via Eureka (`lb://SERVICE-NAME`),
- centraliser la configuration de sécurité, logs, CORS, etc. (optionnel dans ce TP).

---

## 2. Contexte dans l’architecture

Topologie :

- Eureka Server : `http://localhost:8761`
- Gateway : `http://localhost:8888`
- SERVICE-CLIENT : `http://localhost:8088`
- SERVICE-VOITURE : `http://localhost:8089`

Flux d’une requête :

1. Le navigateur appelle la Gateway (ex : `/SERVICE-VOITURE/voitures`).
2. La Gateway résout la route et utilise `lb://SERVICE-VOITURE`.
3. Spring Cloud LoadBalancer choisit une instance de SERVICE-VOITURE.
4. SERVICE-VOITURE appelle éventuellement SERVICE-CLIENT via Feign.
5. La réponse remonte à la Gateway puis au client.

---

## 3. Prérequis

- Java 21+ ou 25
- Maven 3.x

---

## 4. Configuration minimale

Exemple d’`application.properties` :

```properties
spring.application.name=GATEWAY
server.port=8888
```

eureka.client.service-url.defaultZone=http://localhost:8761/eureka





