# TP-Thomas-Detoc-Archi-Logicielle

Projet d’architecture microservices (Spring Boot / Spring Cloud).

## Structure

- **`etape-1/`** — projet Maven multi-modules (parent `pom.xml` + services).

### Lancer le build

```bash
cd etape-1
mvn -DskipTests package
```

### Modules

| Dossier | Rôle |
|---------|------|
| `config-server` | Spring Cloud Config Server |
| `discovery-server` | Eureka Server |
| `api-gateway` | Spring Cloud Gateway |
| `room-service` | Service salles (Web, JPA, H2, Kafka, …) |
| `member-service` | Service membres |
| `reservation-service` | Service réservations |
