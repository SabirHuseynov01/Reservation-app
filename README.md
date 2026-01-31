# Reservation-app

A simple and robust reservation (booking) service built with Java and Spring Boot. This project provides endpoints to create, fetch and cancel reservations for resources. It uses JPA/Hibernate, Lombok, OpenAPI (springdoc) and includes a small JWT utility. Tests are set up to run with Testcontainers.

## Key features

- Create, retrieve and cancel reservations via REST API
- Overlap/conflict detection when creating reservations
- DTO-based API (request/response)
- Optimistic locking via `@Version` on entities
- Centralized exception handling
- OpenAPI (Swagger) configuration
- Testcontainers integration for tests

## Project structure (high-level)

- Controller: `ReservationController` — src/main/java/com/example/reservation/controller/ReservationController.java  
- Service: `ReservationServiceImpl` — src/main/java/com/example/reservation/service/impl/ReservationServiceImpl.java  
- Repository: `ReservationRepository` — src/main/java/com/example/reservation/repository/ReservationRepository.java  
- Entities: `Reservation`, `Resource`, `User` — src/main/java/com/example/reservation/entity/  
- DTOs: `ReservationRequestDTO`, `ReservationResponseDTO` — src/main/java/com/example/reservation/dto/  
- OpenAPI config: `OpenAPIConfig` — src/main/java/com/example/reservation/config/OpenAPIConfig.java  
- JWT helper: `JwtUtil` — src/main/java/com/example/reservation/util/JwtUtil.java  
- Global exception handler: `GlobalExceptionHandler` — src/main/java/com/example/reservation/exception/GlobalExceptionHandler.java

(See the source files in the repository for implementation details.)

## Technologies

- Java (recommended JDK 17+)
- Spring Boot (Web, Data JPA)
- Hibernate / JPA
- Lombok
- springdoc-openapi (OpenAPI / Swagger)
- JWT (utility class)
- Testcontainers (tests)
- Maven

## API (main endpoints)

Base path: /api/reservations

- POST /api/reservations  
  Create a reservation. Request body: ReservationRequestDTO (resourceId, userId, start, end). `start` and `end` are OffsetDateTime and validated to be in the future.

- GET /api/reservations/{id}  
  Fetch reservation by ID.

- POST /api/reservations/{id}/cancel  
  Cancel a reservation (status set to `CANCELLED`).

Example request to create a reservation:
```bash
curl -X POST http://localhost:8080/api/reservations \
  -H "Content-Type: application/json" \
  -d '{
    "resourceId": 1,
    "userId": 1,
    "start": "2026-02-10T10:00:00Z",
    "end":   "2026-02-10T11:00:00Z"
  }'
```

Example response (ReservationResponseDTO):
```json
{
  "id": 123,
  "userId": 1,
  "resourceId": 1,
  "status": "ACCEPTED",
  "start": "2026-02-10T10:00:00Z",
  "end": "2026-02-10T11:00:00Z"
}
```

Error cases:
- Conflict on overlapping reservation → HTTP 409 Conflict (ConflictException)
- Validation errors → HTTP 400 Bad Request

## Running locally

1. Requirements:
   - JDK 17+
   - Maven 3.6+
   - A database (or use Testcontainers for tests)

2. Properties:
   The project reads app properties via `AppProperties`. Provide `app.jwt.secret` at minimum for JWT behavior. Example in application.yml or application.properties:
```yaml
app:
  jwt:
    secret: "change-me-to-a-secure-value"
```

3. Build & run:
```bash
mvn clean package
mvn spring-boot:run
# or run the packaged jar
java -jar target/*.jar
```

4. Tests:
```bash
mvn test
```

5. OpenAPI / Swagger:
If `springdoc-openapi-ui` is present, Swagger UI is typically available at:
- /swagger-ui/index.html
OpenAPI JSON/YAML:
- /v3/api-docs

## Design notes / considerations
- Conflict detection is implemented in the service layer (`ReservationServiceImpl`) by querying overlapping accepted reservations and throwing `ConflictException`.
- `@Version` on entities provides optimistic locking for concurrent updates.
- DTOs decouple API contract from internal entities.
- Global exception handler centralizes error responses.
- `JwtUtil` and `AppProperties` are present to support JWT-based authentication if you add security filters.

## Suggested improvements
- Add database migrations (Flyway or Liquibase).
- Add more unit and integration tests (especially for concurrency and conflict detection).
- Document authentication flow and secure endpoints with JWT filters if needed.
- Add CI (GitHub Actions) for build and test automation.
- Provide Dockerfile and docker-compose for quick local setup.

## Contributing
- Add a LICENSE file (e.g., MIT) if you want to make the project reusable.
- If you want, I can prepare the README and open a PR to add it to the repository.

---
