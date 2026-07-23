# Task Management REST API

A Spring Boot microservice for managing tasks — full CRUD, layered architecture (controller → service → repository), validation, global exception handling, and unit tests.

## Tech Stack
Java 17 · Spring Boot 3.2 · Spring Data JPA · SQL Server (H2 for local dev) · JUnit 5 · Mockito · Maven

## Run it locally
```bash
mvn spring-boot:run
```
Starts on `http://localhost:8080` using an in-memory H2 database — no install needed.

## Run the tests
```bash
mvn clean test
```

## Switch to SQL Server
Edit `src/main/resources/application.properties` — comment out the H2 block, uncomment the SQL Server block, fill in your credentials.

## API Endpoints
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/tasks` | List all tasks |
| GET | `/api/tasks?status=IN_PROGRESS` | Filter by status |
| GET | `/api/tasks/{id}` | Get one task |
| POST | `/api/tasks` | Create a task |
| PUT | `/api/tasks/{id}` | Update a task |
| DELETE | `/api/tasks/{id}` | Delete a task |
