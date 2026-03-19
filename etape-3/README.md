# Étape 3 — Kafka

- **room-events** : `ROOM_DELETED` à la suppression d’une salle.
- **member-events** : `MEMBER_DELETED` à la suppression d’un membre ; `MEMBER_SUSPEND` / `MEMBER_UNSUSPEND` depuis reservation-service ; **member-service** consomme suspend/unsuspend.
- **reservation-service** : annule les réservations si salle supprimée, supprime les réservations si membre supprimé, publie suspend/unsuspend selon le nombre de réservations **CONFIRMED** et `maxConcurrentBookings`.

Kafka : `localhost:9092`. Config Java par service (`kafka/KafkaConfig.java`) : bootstrap, `group-id` dédié, `StringSerializer` + `JsonSerializer` côté producer ; consumers avec `JsonDeserializer`.

Build : `cd etape-3 && mvn -DskipTests package` (Java 17).
