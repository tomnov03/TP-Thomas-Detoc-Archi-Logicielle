# Étape 2 — Réservations + RestTemplate

- **reservation-service** (8083) : entité `Reservation`, création avec appels `member-service` et `room-service`, chevauchement JPQL, annulation / complétion.
- **member-service** : `GET /members/{id}`, `GET /members/{id}/can-book`, `DELETE /members/{id}`.
- **room-service** : `GET /rooms/{id}`, `PUT /rooms/{id}` (body `{ "available": bool }`), `DELETE /rooms/{id}`.

Build : `cd etape-2 && mvn -DskipTests package` (Java 17).

Démarrage : config → discovery → gateway → room, member, reservation. Appels inter-services via noms Eureka (`http://member-service/...`).
