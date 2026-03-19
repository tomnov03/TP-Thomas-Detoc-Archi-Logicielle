# TP-Thomas-Detoc-Archi-Logicielle

Projet d’architecture microservices (Spring Boot 3.2.x, Spring Cloud 2023.0.x).

## Contenu

- **`etape-1/`** — squelette multi-modules (voir [`etape-1/README.md`](etape-1/README.md)).
- **`etape-2/`** — réservations + RestTemplate (`etape-2/README.md`).
- **`etape-3/`** — Kafka (`etape-3/README.md`).
- **`etape-4/`** — pattern State sur les réservations (`etape-4/README.md`).
- **`etape-5/`** — Config Server (native `config-repo`), Eureka, Gateway `lb://` + `StripPrefix`, clients avec `bootstrap.properties`.
- **`etape-6/`** — comme l’étape 5 + documentation **Swagger / springdoc** sur les trois services métier.
- **`etape-7/`** — comme l’étape 6 + **`README.md`** détaillé (prérequis, ordre de démarrage, ports, `curl`) : [`etape-7/README.md`](etape-7/README.md).

## Démarrage rapide

```bash
cd etape-1
mvn -DskipTests package
```

**Java 17** requis (vérifié au build par le plugin Maven Enforcer).

Un `pom.xml` à la **racine du dépôt** agrège uniquement **`etape-7`** (évite les conflits Maven quand plusieurs étapes réutilisent les mêmes noms de modules). Pour une autre étape, ouvrez son dossier dans l’IDE ou lancez `mvn` depuis `etape-1` … `etape-6`. Vous pouvez aussi ouvrir `open-etape-4.code-workspace` pour l’étape 4 seule.

Les fichiers générés Maven (`target/`) ne sont pas versionnés (voir `.gitignore`).
