# Digital Health Backend — Technical Documentation

## 1. Quick Start

### Prerequisites

The application requires:

* Java 17
* Maven 3.x or later

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

---

### Start the Application

From the project root directory:

```bash
mvn spring-boot:run
```

The application starts on:

```text
http://localhost:8080
```

Once the application has started successfully, the following services are available.

### Swagger UI

Interactive API documentation:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger UI can be used to:

* View all available endpoints
* Inspect request and response schemas
* Test API operations
* Provide the required `X-API-Key` header
* Explore the OpenAPI-generated API specification

### OpenAPI Specification

The machine-readable OpenAPI specification is available at:

```text
http://localhost:8080/v3/api-docs
```

### H2 Database Console

The H2 development database can be inspected through:

```text
http://localhost:8080/h2-console
```

Use the following development connection settings:

```text
JDBC URL: jdbc:h2:mem:exercise
Username: admin
Password: admin123
```

### Run Tests

The application test suite can be executed with:

```bash
mvn test
```



---

# 2. Application Overview

The Digital Health Backend is a RESTful Spring Boot application for managing core clinical data.

The application provides APIs for:

* Patient management
* Encounter management
* Clinical observations
* Patient search
* Pagination and sorting
* Request validation
* Predictable API error responses
* API-key authentication
* OpenAPI/Swagger documentation

The application follows a layered architecture separating API handling, business logic, persistence, and domain models.

---

# 3. Technology Stack

| Technology        | Purpose                         |
| ----------------- | ------------------------------- |
| Java 17           | Application runtime             |
| Spring Boot 3.3.4 | Application framework           |
| Spring Web        | REST API                        |
| Spring Data JPA   | Persistence                     |
| Hibernate         | ORM                             |
| H2                | Development database            |
| Maven             | Build and dependency management |
| JUnit 5           | Testing                         |
| Mockito           | Unit testing                    |
| MockMvc           | Controller/API testing          |
| Springdoc OpenAPI | API documentation               |
| Lombok            | Boilerplate reduction           |

---

# 4. Architecture

The application follows a layered architecture:

```text
                    Client
                      │
                      ▼
              ┌───────────────┐
              │   Controller  │
              └───────┬───────┘
                      │
                      ▼
              ┌───────────────┐
              │    Service    │
              └───────┬───────┘
                      │
                      ▼
              ┌───────────────┐
              │   Repository  │
              └───────┬───────┘
                      │
                      ▼
              ┌───────────────┐
              │ H2 Database   │
              └───────────────┘
```

### Controller Layer

Responsible for:

* HTTP request handling
* Path variables
* Query parameters
* Request bodies
* HTTP response status codes
* Request validation
* Delegating operations to services

### Service Layer

Responsible for:

* Business logic
* Entity creation and updates
* Relationship validation
* Resource existence checks
* Business rules
* Entity-to-DTO mapping

### Repository Layer

Responsible for:

* Database access
* CRUD operations
* Search queries
* Pagination
* Sorting

---

# 5. Project Structure

```text
starter-project/
├── pom.xml
├── README.md
├── DOCUMENTATION.md
├── EXERCISE.md
└── src/
    ├── main/
    │   ├── java/
    │   │   └── org/example/
    │   │       ├── controller/
    │   │       ├── dto/
    │   │       ├── entity/
    │   │       ├── repository/
    │   │       ├── service/
    │   │       └── security/
    │   │
    │   └── resources/
    │       └── application.properties
    │
    └── test/
        └── java/
            └── org/example/
```

---

# 6. Domain Model

The application currently models three primary clinical resources:

```text
Patient
   │
   ├──< Encounter
   │       │
   │       └──< Observation
   │
   └──< Observation
```

## Patient

A patient represents an individual receiving healthcare services.

Important fields:

* `id`
* `identifier`
* `givenName`
* `familyName`
* `birthDate`
* `gender`

The patient identifier is generated as a UUID and is unique.

## Encounter

An encounter represents a healthcare interaction involving a patient.

Important fields:

* `id`
* `patient`
* `start`
* `end`
* `encounterClass`

Supported encounter classes:

* `AMBULATORY`
* `INPATIENT`
* `OUTPATIENT`
* `VIRTUAL`

## Observation

An observation represents a clinical measurement or recorded finding.

Important fields:

* `id`
* `patient`
* `encounter`
* `code`
* `value`
* `effectiveDateTime`

An observation must belong to a patient.

An observation may optionally be associated with an encounter.

---

# 7. Entity Relationships

## Patient → Encounter

One patient can have multiple encounters.

```text
Patient 1 ───────── * Encounter
```

## Patient → Observation

One patient can have multiple observations.

```text
Patient 1 ───────── * Observation
```

## Encounter → Observation

An observation may optionally belong to an encounter.

```text
Encounter 1 ─────── * Observation
```

An encounter with associated observations cannot be deleted directly. This prevents deletion of an encounter while dependent clinical observations still reference it.

---

# 8. REST API

The API base path is:

```text
/api
```

## Patient Endpoints

### Create Patient

```http
POST /api/patients
```

Example request:

```json
{
  "givenName": "John",
  "familyName": "Mwangi",
  "birthDate": "1992-06-15",
  "gender": "MALE"
}
```

The server generates the patient's UUID identifier.

---

### Get Patient

```http
GET /api/patients/{id}
```

Example:

```http
GET /api/patients/1
```

---

### Get Patients

```http
GET /api/patients
```

Pagination example:

```http
GET /api/patients?page=0&size=10
```

Sorting example:

```http
GET /api/patients?page=0&size=10&sort=familyName,asc
```

---

### Search Patients

Patient search supports available patient attributes including:

```text
family
given
identifier
birthDate
```

Example:

```http
GET /api/patients?family=Mwangi
```

Search can be combined with pagination and sorting:

```http
GET /api/patients?family=Mwangi&page=0&size=10&sort=familyName,asc
```

---

### Update Patient

```http
PUT /api/patients/{id}
```

Example:

```json
{
  "givenName": "John",
  "familyName": "Mwangi",
  "birthDate": "1992-06-15",
  "gender": "MALE"
}
```

---

### Delete Patient

```http
DELETE /api/patients/{id}
```

---

# 9. Encounter API

### Create Encounter

```http
POST /api/patients/{patientId}/encounters
```

Example:

```json
{
  "start": "2026-09-30T09:30:00",
  "end": "2026-09-30T10:30:00",
  "encounterClass": "OUTPATIENT"
}
```

### Get Patient Encounters

```http
GET /api/patients/{patientId}/encounters
```

### Get Encounter

```http
GET /api/encounters/{id}
```

### Update Encounter

```http
PUT /api/encounters/{id}
```

Example:

```json
{
  "start": "2026-09-30T09:30:00",
  "end": "2026-09-30T10:30:00",
  "encounterClass": "VIRTUAL"
}
```

The existing patient relationship is preserved during an encounter update.

### Delete Encounter

```http
DELETE /api/encounters/{id}
```

An encounter with associated observations cannot be deleted until its observations have been handled.

---

# 10. Observation API

### Add Observation

```http
POST /api/patients/{patientId}/observations
```

Example:

```json
{
  "encounterId": 1,
  "code": "blood-pressure",
  "value": "120/80 mmHg",
  "effectiveDateTime": "2026-09-30T09:15:00"
}
```

`encounterId` is optional.

An observation can therefore exist without an associated encounter.

### Get Patient Observations

```http
GET /api/patients/{patientId}/observations
```

Observations are returned ordered by effective date/time.

---

# 11. API Authentication

The API uses a simple API-key authentication mechanism.

Clients provide the key using:

```http
X-API-Key: <api-key>
```

Example:

```bash
curl \
  -H "X-API-Key: my-secret-key" \
  http://localhost:8080/api/patients
```

Requests without a valid API key return:

```http
401 Unauthorized
```

For deployed environments, the API key should be supplied through an environment variable rather than committed as a source-code secret.

---

# 12. OpenAPI / Swagger

Interactive API documentation is provided through Swagger UI.

Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

OpenAPI specification:

```text
http://localhost:8080/v3/api-docs
```

Swagger provides an interactive representation of the implemented API, including:

* HTTP methods
* Endpoint paths
* Parameters
* Request bodies
* Response schemas
* Authentication requirements
* Interactive API execution

The OpenAPI documentation is generated from the application's API configuration and controllers.

---

# 13. Request Validation

Patient requests use Bean Validation.

Required patient fields include:

* Given name
* Family name
* Birth date
* Gender

The birth date must be in the past.

Invalid requests return:

```http
400 Bad Request
```

Example validation response:

```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Request contains invalid fields",
  "errors": {
    "givenName": "Given name is required",
    "birthDate": "Birth date must be in the past"
  }
}
```

---

# 14. Error Handling

The API uses standard HTTP status codes.

| Status                      | Meaning                                    |
| --------------------------- | ------------------------------------------ |
| `200 OK`                    | Successful operation                       |
| `201 Created`               | Resource created                           |
| `400 Bad Request`           | Invalid request                            |
| `401 Unauthorized`          | Missing or invalid API key                 |
| `404 Not Found`             | Resource does not exist                    |
| `409 Conflict`              | Operation violates a resource relationship |
| `500 Internal Server Error` | Unexpected server error                    |

---

# 15. Pagination and Sorting

Patient listing supports Spring Data pagination.

Example:

```http
GET /api/patients?page=0&size=10
```

Sorting:

```http
GET /api/patients?page=0&size=10&sort=familyName,asc
```

The response contains the patient content together with pagination metadata such as:

* Current page
* Page size
* Total elements
* Total pages
* Sorting information

---

# 16. Database

The application currently uses H2 for development and testing.

Configuration:

```text
Database: H2
Mode: In-memory
Database: exercise
```

H2 console:

```text
http://localhost:8080/h2-console
```

Development connection:

```text
JDBC URL: jdbc:h2:mem:exercise
Username: admin
Password: admin123
```

Hibernate manages the development schema.

---

# 17. Testing

Run all tests:

```bash
mvn test
```

The project uses:

* JUnit 5
* Mockito
* Spring Test
* MockMvc
* AssertJ

Unit tests do not require the Spring Boot application to be running.

For example, `PatientServiceImplTest` uses Mockito to isolate the service layer from the database.

A successful test execution reports:

```text
Tests run: 1
Failures: 0
Errors: 0
Skipped: 0

BUILD SUCCESS
```

The test suite is being expanded to cover:

* Patient creation
* Patient retrieval
* Patient updates
* Patient deletion
* Patient search
* Not-found scenarios
* Encounter operations
* Observation operations
* Encounter/observation relationship rules
* Controller/API behaviour

---

# 18. Business Rules

## Patient

* Patient names are required.
* Birth date is required.
* Birth date must be in the past.
* Gender is required.
* Patient identifiers are generated by the server.
* Patient identifiers are unique.

## Encounter

* An encounter must belong to an existing patient.
* An encounter has a start time.
* An encounter has an encounter class.
* Updating an encounter does not change its patient.
* An encounter with associated observations cannot be deleted.

## Observation

* An observation must belong to a patient.
* An observation requires a code.
* An observation requires a value.
* An observation requires an effective date/time.
* An observation may optionally reference an encounter.
* An observation's encounter must belong to the same patient.

---

# 19. Security Considerations

The current API-key mechanism provides lightweight authentication appropriate for this exercise.

A production deployment should additionally consider:

* HTTPS/TLS
* Secure secret management
* API-key rotation
* Role-based authorization
* Audit logging
* Rate limiting
* Production database credentials
* Database encryption
* Backup and recovery procedures

Real production credentials should never be committed to source control.

---

# 20. Development vs Production Database

H2 is used for local development and testing because it is lightweight and requires no separate database server.

The persistence layer uses Spring Data JPA, allowing the application to be configured against PostgreSQL or another relational database for production without changing the core service architecture.

Production environments should use persistent storage and a proper database migration strategy such as Flyway or Liquibase.

---

# 21. Design Decisions

### DTO-based API

The API uses DTOs rather than exposing JPA entities directly.

This provides separation between:

* Persistence models
* API contracts

and prevents database relationships from unnecessarily becoming part of the external API model.

### Layered architecture

Controllers delegate business operations to services, while repositories handle persistence.

This keeps business rules out of HTTP controllers and makes the service layer easier to unit test.

### Nested resource creation

Encounters and observations are created under a patient:

```text
/api/patients/{patientId}/encounters
/api/patients/{patientId}/observations
```

This makes the ownership relationship explicit.

Individual encounter operations use:

```text
/api/encounters/{id}
```

This separates patient-scoped creation/retrieval from operations on an individual encounter.

---

# 22. API Documentation Strategy

Two complementary forms of documentation are provided.

### Human-readable documentation

This document explains:

* Architecture
* Domain model
* Relationships
* Business rules
* Security
* Database configuration
* Testing
* Development workflow

### OpenAPI documentation

Swagger/OpenAPI provides the executable API contract:

```text
http://localhost:8080/swagger-ui/index.html
```

This allows developers and reviewers to inspect and interact with the actual API implementation.

---

# 23. Future Improvements

Potential production improvements include:

* PostgreSQL production configuration
* Docker/Docker Compose deployment
* Database migrations with Flyway or Liquibase
* More comprehensive integration testing
* Advanced patient date-range filtering
* Role-based authorization
* Audit logging
* Structured application logging
* Health checks and monitoring
* CI/CD automation
* Production secret management
* API rate limiting

---

# 24. Summary

The Digital Health Backend provides a RESTful API for managing:

Patient
   │
   ├── Encounter
   │      │
   │      └── Observation
   │
   └── Observation
```

The application combines:

* Java 17
* Spring Boot
* Spring Data JPA
* Hibernate
* H2
* Bean Validation
* API-key authentication
* OpenAPI/Swagger
* JUnit
* Mockito
* RESTful API design

The recommended starting point for API consumers is Swagger UI:

```text
http://localhost:8080/swagger-ui/index.html
```

The detailed architecture, business rules, database configuration, security model, and testing approach are documented in this file.
