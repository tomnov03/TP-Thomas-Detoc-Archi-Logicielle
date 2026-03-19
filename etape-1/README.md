# Étape 1 — Multi-module Spring Boot / Spring Cloud

## Prérequis

- **Java 17** (obligatoire : le parent POM impose `[17,18)` via Maven Enforcer)
- **Maven 3.9+**
- **Kafka** (optionnel pour démarrer les services : la santé Kafka est désactivée ; pour utiliser Kafka, lance un broker sur `localhost:9092` ou définis `KAFKA_BOOTSTRAP_SERVERS`)

## Build

```bash
cd etape-1
mvn -DskipTests package
```

## Ordre de démarrage conseillé

1. `config-server` (port **8888**)
2. `discovery-server` / Eureka (port **8761**)
3. `api-gateway` (port **8080**)
4. Les services métier : `room-service` (**8081**), `member-service` (**8082**), `reservation-service` (**8083**)

Exemple :

```bash
mvn -pl config-server spring-boot:run
mvn -pl discovery-server spring-boot:run
mvn -pl api-gateway spring-boot:run
mvn -pl room-service spring-boot:run
# … autres services dans d’autres terminaux
```

## API Gateway

La découverte **Eureka** est activée sur la gateway (`discovery.locator`).  
Exemple d’URL une fois les services enregistrés :

- `http://localhost:8080/room-service/actuator/health`
- `http://localhost:8080/member-service/actuator/health`
- `http://localhost:8080/reservation-service/actuator/health`

Les routes suivent le pattern `/{service-id}/...` (identifiants en minuscules).

## Config Server

Profil **native** : fichiers partagés sous `config-server/src/main/resources/config/`  
(le fichier `application.yml` y définit notamment `spring.jpa.hibernate.ddl-auto` pour les clients Config).

## Modules

| Module | Rôle |
|--------|------|
| `config-server` | Spring Cloud Config Server |
| `discovery-server` | Netflix Eureka Server |
| `api-gateway` | Spring Cloud Gateway + Eureka + LoadBalancer |
| `room-service` | Web, JPA, H2, Config client, Eureka, Kafka, Actuator |
| `member-service` | Idem |
| `reservation-service` | Idem |
