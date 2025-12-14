# CatalogX Platform

CatalogX is a **containerized, event-driven microservices platform** built using **Spring Boot**, **Kafka**, and **Docker**, following **CQRS** and **Saga (Choreography) patterns**.  
The project is designed to demonstrate **real-world microservice architecture**, **eventual consistency**, and **secure API access using JWT authentication**.

---

## 🧠 Architecture Overview

### Design Patterns Used

### ✅ CQRS (Command Query Responsibility Segregation)

The system clearly separates **write operations (commands)** from **read operations (queries)**.

**Write Side (Command Services)**
- product-service
- inventory-service
- order-service
- cart-service

Each write service:
- Owns its **own PostgreSQL database**
- Handles command-based operations (create, update, reserve, etc.)
- Publishes domain events to Kafka

**Read Side (Query Service)**
- search-service

The search-service:
- Consumes Kafka events
- Builds **read-optimized projections**
- Stores data in **MongoDB**

This approach improves scalability, performance, and maintainability.

---

### ✅ Saga Pattern (Choreography)

The platform implements the **Saga Choreography pattern** (not orchestration).

- No central saga coordinator
- Each service reacts to Kafka events independently
- Ensures **eventual consistency** across services

**Example Flow**
1. order-service publishes `ORDER_CREATED`
2. inventory-service consumes the event and publishes `INVENTORY_RESERVED`
3. Other services react based on their responsibilities

✔ Event-driven  
✔ Decentralized  
✔ Fault-tolerant

---

## 🧩 Microservices

| Service | Responsibility | Database | Port |
|-------|---------------|----------|------|
| api-gateway | Entry point, JWT validation, Swagger aggregation | — | 9000 |
| eureka-server | Service registry | — | 8761 |
| product-service | Product & Category management | PostgreSQL | 9001 |
| inventory-service | Inventory & stock management | PostgreSQL | 9002 |
| order-service | Order processing | PostgreSQL | 9003 |
| cart-service | Cart operations | PostgreSQL | 9004 |
| search-service | Read projections & search | MongoDB | 9005 |

---

## 🔐 Security

- JWT-based authentication
- All business APIs are **secured**
- Requests **must go through API Gateway**
- Swagger and OpenAPI endpoints are **public (read-only)**

---

## 📡 Event Streaming (Kafka)

### Topics Used
- product-events
- inventory-events
- order-events

### Purpose
- Propagate state changes
- Enable Saga Choreography
- Build read-side projections

---

## 🗄 Databases

### PostgreSQL
- Used by all write-side services
- Schema versioning via **Flyway migrations**

### MongoDB
- Used only by search-service
- Stores denormalized, query-optimized data

---

## 🐳 Docker & Containerization

The **entire platform is containerized** using Docker and Docker Compose.

Included containers:
- All microservices
- Kafka & Zookeeper
- PostgreSQL (per service)
- MongoDB
- Eureka Server
- API Gateway

---

## 🚀 How to Run the Project

### 1️⃣ Clone the Repository
```bash
git clone git@github.com:nithinraaj27/catalogx-platform.git
cd catalogx-platform
```

---

### 2️⃣ Build All Services
```bash
./mvnw clean package -DskipTests
```

---

### 3️⃣ Start the Platform
```bash
docker-compose up --build
```

⏳ Initial startup may take a few minutes (Kafka and Eureka need time).

---

### 4️⃣ Verify Services

- Eureka Dashboard  
  http://localhost:8761

- API Gateway  
  http://localhost:9000

---

## 📘 Swagger (Aggregated via API Gateway)

All APIs are available through a **single Swagger UI** exposed by the API Gateway.

👉 Swagger URL
```
http://localhost:9000/swagger-ui/index.html
```

---

## 🧪 Postman Usage

Only the provided **Postman collection** is required.

### Steps
1. Import the Postman collection
2. Run the **Authentication / Token API** first
3. JWT token is automatically stored using a **collection-level script**
4. All other APIs reuse the token automatically

⚠️ Do not manually set JWT tokens.

---

## 📂 Project Structure

```text
catalogx-platform
│
├── api-gateway
├── eureka-server
├── product-service
├── inventory-service
├── order-service
├── cart-service
├── search-service
│
├── docker-compose.yml
├── pom.xml
├── README.md
└── init-mongo.js
```

Each service follows a clean layered structure:
- controller
- service
- repository
- security
- dto
- events (producer / consumer)
- flyway migrations

---

## ⭐ Key Highlights

- CQRS Architecture
- Saga Choreography Pattern
- Kafka-based event streaming
- JWT-secured APIs
- Aggregated Swagger UI
- Fully Dockerized setup
- Production-like microservice design

---

## 👤 Usage Notes

- Designed to run locally using Docker
- Postman is the primary API client
- Swagger is provided for exploration and documentation

---

## 🎯 Ideal For

- Backend / Java Developers
- Microservices learners
- Kafka & event-driven architecture practice
- Interview-ready system design demonstration

---

Happy Coding 🚀

