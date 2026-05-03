# db-mastery

A multi-module Gradle project for deep-diving into database technologies with Spring Boot.
Each module is a self-contained Spring Boot app targeting a specific database — run them independently,
compare patterns, and build POCs without one DB bleeding into another.

## Project Structure

```
db-mastery/
├── shared/              # Common domain models (User, Product) reused across modules
├── jpa-postgres/        # Spring Data JPA + PostgreSQL + Flyway migrations
├── mongodb/             # (coming soon) Spring Data MongoDB
├── dynamodb/            # (coming soon) AWS SDK + DynamoDB
└── cosmosdb/            # (coming soon) Spring Data CosmosDB
```

## Prerequisites

- Java 21+
- Docker (for running databases locally)
- IntelliJ IDEA (recommended)

## Running a Module

### 1. Start the database

```bash
cd jpa-postgres
docker compose up -d
```

### 2. Run the Spring Boot app

```bash
# From project root
./gradlew :jpa-postgres:bootRun
```

Or open in IntelliJ and run `JpaPostgresApplication` directly.

### 3. Hit the API

```bash
# Create a user
curl -X POST http://localhost:8081/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Ravi","email":"ravi@example.com","role":"ADMIN"}'

# List all users
curl http://localhost:8081/api/users
```

## Running Tests

```bash
# All modules
./gradlew test

# Single module
./gradlew :jpa-postgres:test
```

Tests use Testcontainers — Docker must be running.

## Adding a New DB Module

1. Create a new folder: `mkdir -p newdb/src/main/java/com/dbmastery/newdb`
2. Add a `build.gradle.kts` with the relevant dependencies
3. Register it in `settings.gradle.kts`: add `"newdb"` to the `include(...)` list
4. Add `docker-compose.yml` for the database
5. Add `application.yml` with connection config (use a unique `server.port`)

## Module Port Map

| Module        | Port |
|---------------|------|
| jpa-postgres  | 8081 |
| mongodb       | 8082 |
| dynamodb      | 8083 |
| cosmosdb      | 8084 |
