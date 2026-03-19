# TP-Thomas-Detoc-Archi-Logicielle

Projet d’architecture microservices (Spring Boot 3.2.x, Spring Cloud 2023.0.x).

## Contenu

- **`etape-1/`** — projet Maven multi-modules : Config Server, Eureka, API Gateway, services métier (voir [`etape-1/README.md`](etape-1/README.md)).

## Démarrage rapide

```bash
cd etape-1
mvn -DskipTests package
```

**Java 17** requis (vérifié au build par le plugin Maven Enforcer).

Les fichiers générés Maven (`target/`) ne sont pas versionnés (voir `.gitignore`).
