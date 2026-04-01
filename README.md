# TaskFlow – Modern Spring Boot Backend (JWT, Security,  Testing, Flyway)

TaskFlow is a production‑grade backend built with **Spring Boot 3**, designed to demonstrate real‑world engineering practices including authentication, authorization, caching, observability, testing, and database migrations.

This project is part of a larger system that will evolve into a **Microservices + Kafka + Docker** architecture.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- JWT‑based authentication (access token)
- Role‑based authorization (`ROLE_USER`, `ROLE_ADMIN`)
- Secure endpoints with Spring Security 6
- Custom JWT filter + AuthenticationManager

### 🧩 Clean Architecture
- Controller → Service → Repository layers
- DTOs + Mappers for clean API responses
- Global API Response Wrapper
- Exception Handling (Global)

### ⚡ Performance & Observability
- Caching for GET endpoints
- ETag support for conditional requests
- Execution time logging
- Structured logs

### 🗄 Database & Migrations
- Flyway migrations (`V1__init.sql`, `V2__seed_roles.sql`)
- H2 for development/testing
- Ready for PostgreSQL/MySQL in production

### 🧪 Testing
- Unit tests (Service layer)
- Integration tests (Controller + MockMvc)
- Security tests (401/403 flows)
- Repository tests with H2

---

## 📁 Project Structure

src/
├── main/java/com/taskflow
│    ├── controller
│    ├── service
│    ├── repository
│    ├── security
│    ├── dto
│    ├── mapper
│    └── config
└── test/java/com/taskflow
├── controller
├── service
├── repository
└── security



---

## 🔑 Authentication Flow

1. **Register** → `/auth/register`
2. **Login** → `/auth/login`
3. Receive JWT token
4. Use token in header:


---

## 🧪 Running Tests

mvn Test


All tests run against H2 with Flyway migrations applied automatically.

---

## 🛠 Tech Stack

- Java 17
- Spring Boot 3
- Spring Security 6
- JWT
- Flyway
- H2 / PostgreSQL
- Maven
- JUnit 5 + Mockito
- MockMvc

---

## 🌱 Future Roadmap

- Convert to Microservices (Spring Cloud)
- API Gateway + Service Registry
- Kafka event streaming
- Docker + Docker Compose
- React/Next.js frontend
- CI/CD pipeline

---


