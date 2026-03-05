# Distributed Delivery Tracking System

A backend system for managing and tracking package deliveries across cities.

This project simulates a real-world logistics platform where shipments can be created, tracked, and managed through REST APIs.
It demonstrates **production-level backend architecture using Spring Boot**.

---

# Project Goals

This project was built to practice and demonstrate:

- Advanced REST API design
- Clean architecture principles
- Database migrations
- API documentation
- Production-ready backend practices

It is also the foundation for a scalable delivery platform that can later evolve into **microservices**.

---

# Tech Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Data JPA
- Spring Security

### Database

- PostgreSQL

### Infrastructure

- Docker
- Docker Compose
- Flyway (database migrations)

### API Documentation

- OpenAPI / Swagger

### Testing (coming soon)

- JUnit 5
- Mockito
- Testcontainers

---

# Project Architecture

The project follows a **layered architecture**:

Controller → Service → Repository → Database

### Structure

```
src/main/java/com/moa/shipment_service

controller   → REST endpoints
service      → business logic
repository   → database access
entity       → JPA entities
dto          → request / response models
mapper       → entity ↔ DTO conversion
config       → configuration classes
```

---

# Running the Project

### 1. Start PostgreSQL using Docker

Run:

```
docker compose up -d
```

This starts a PostgreSQL database container.

---

### 2. Run the Spring Boot application

Start the application from your IDE or run:

```
./mvnw spring-boot:run
```

The API will start on:

```
http://localhost:8081
```

---

# API Documentation

Swagger UI is available at:

```
http://localhost:8081/swagger-ui/index.html
```

From there you can:

- explore endpoints
- test API requests
- see request and response models

---

# Database Migrations

This project uses **Flyway** to manage database schema changes.

Migration files are located in:

```
src/main/resources/db/migration
```

Example migration:

```
V1__create_shipments_table.sql
```

Flyway automatically applies migrations when the application starts.

---

# Example API

### Create Shipment

POST `/shipments`

Request

```
{
  "senderName": "Ayoub",
  "receiverName": "Yazid",
  "originCity": "Oum El Bouaghi",
  "destinationCity": "Tizi Ouzou"
}
```

Response

```
{
  "id": 1,
  "trackingNumber": "3f3d3d1a-5a1f-4d2a-bf6a-2b9d8e71a1c3",
  "status": "CREATED",
  "createdAt": "2026-03-05T10:00:00",
  "originCity": "Oum El Bouaghi",
  "destinationCity": "Tizi Ouzou"
}
```

---

# Features Implemented

- Shipment creation
- Tracking number generation
- PostgreSQL integration
- Flyway migrations
- DTO pattern
- Entity mapping
- Swagger API documentation
- Dockerized database

---

# Future Improvements

Planned features:

- Shipment status updates
- Shipment search with pagination
- Authentication with JWT
- Integration tests
- Redis caching
- Microservices architecture
- Kafka event streaming
- Kubernetes deployment

---

# Author

**Mohamed Ayoub ATTALAH**

Backend Developer (Java / Spring Boot)
