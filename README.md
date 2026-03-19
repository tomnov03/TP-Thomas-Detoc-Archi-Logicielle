# TP-Thomas-Detoc-Archi-Logicielle

Projet d’architecture microservices (Spring Boot 3.2.x, Spring Cloud 2023.0.x).

## Contenu

- **`etape-1/`** — squelette multi-modules (voir [`etape-1/README.md`](etape-1/README.md)).
- **`etape-2/`** — réservations + RestTemplate (`etape-2/README.md`).
- **`etape-3/`** — Kafka (`etape-3/README.md`).
- **`etape-4/`** — pattern State sur les réservations (`etape-4/README.md`).

## Démarrage rapide

```bash
cd etape-1
mvn -DskipTests package
```

**Java 17** requis (vérifié au build par le plugin Maven Enforcer).

Les fichiers générés Maven (`target/`) ne sont pas versionnés (voir `.gitignore`).
