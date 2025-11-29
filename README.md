## Product Catalog

👉 **Java version: 17 (LTS)**

👉 Spring **version:  3.5.8**

### **Core Framework**

- Spring Boot **3.5.x**
- Spring Cloud **2023.0.x**

### **Service Architecture**

- **Eureka** (Service Registry)
- **Spring Cloud Gateway** (API Gateway)
- **Spring Cloud Config** (Centralized Config)

### **Observability**

- **Micrometer Tracing + Zipkin** (Distributed Tracing)
- **Prometheus + Grafana** (Metrics + Dashboards)

### **Resilience**

- **Resilience4j** (Circuit Breaker, Retry, Bulkhead, Rate Limiter)

### **Messaging**

- **Kafka** + **Spring Kafka**

### **Databases**

- **PostgreSQL**
- **MongoDB** (only if needed)
- **Flyway** or **Liquibase** (DB migration)

### **Persistence & Backend**

- **JPA/Hibernate**
- **Lombok**
- **MapStruct** (optional)

### **Containerization**

- **Docker & Docker Compose**

### **Testing**

- **Testcontainers** (Kafka, Postgres, Mongo)

## Service List

1. **eureka-server**
2. **config-server**
3. **gateway** (Spring Cloud Gateway)
4. **auth-service** (user / auth / token management)
5. **product-catalog-service**
6. **inventory-service**
7. **order-service**
8. **search-service** (read-model / search)
9. **image-service** (media handling)
10. **notification-service** (email/SMS/webhook)
11. **billing-service** (invoicing / payments — optional later)
12. **zipkin** (tracing infra) — treated as infra service
13. **kafka** (broker infra) — infra
14. **postgres** (infra DB instance(s)) — infra
15. **mongo** (infra, optional) — infra
16. **prometheus** & **grafana** (monitoring infra) — infra

## Folder Structure

```swift
/ (repo root)
├─ .github/                       # CI/CD workflows (kept, but no content now)
├─ infra/                         # Docker-compose, k8s manifests, infra configs
│  ├─ docker-compose.yml
│  ├─ k8s/                        # optional k8s manifests/helm charts
│  └─ scripts/
├─ platform/                      # infrastructure microservices (spring apps)
│  ├─ eureka-server/
│  ├─ config-server/
│  └─ gateway/
├─ services/                      # all application microservices (spring apps)
│  ├─ auth-service/
│  ├─ product-catalog-service/
│  ├─ inventory-service/
│  ├─ order-service/
│  ├─ search-service/
│  ├─ image-service/
│  ├─ notification-service/
│  └─ billing-service/
├─ libs/                          # shared java modules: models, events, common utilities
│  ├─ libs/model-contracts/       # shared DTOs and event schemas
│  ├─ libs/common-config/         # shared configuration helpers
│  └─ libs/kafka-utils/           # producers/consumers helpers, serializers
├─ deployments/                    # production deployment manifests / helm
├─ docs/                           # architecture docs, event contracts (only)
└─ README.md

```

### Per-service rule

- Every service folder under `platform/` or `services/` **must** contain exactly the same minimal top-level files (keep names consistent):
    
    ```
    service-name/
    ├─ src/
    ├─ Dockerfile
    ├─ pom.xml (or build.gradle)
    ├─ application.yml (skeleton in resources)
    └─ README.md
    
    ```
    
- Shared code ONLY goes into `libs/` and is imported as a dependency by services. Do not copy shared code across services.

# Dependency List

## Master - Project

- Spring Web
- Spring Data JPA
- Spring Data MongoDB (if needed)
- PostgreSQL Driver
- Flyway
- Spring Cloud Netflix Eureka Client
- Spring Cloud Config Client
- Spring Kafka
- Resilience4j
- Micrometer Tracing (Brave)
- Zipkin Reporter
- Micrometer Prometheus Registry
- Spring Boot Actuator
- Lombok
- MapStruct
- Spring Boot Starter Test
- Testcontainers

## Service Ports

# ✅ **Microservices & Database Port Table**

| Service Name | Service Port | Database Type | DB Port |
| --- | --- | --- | --- |
| **eureka-server** | 8761 | — | — |
| **config-server** | 8888 | — | — |
| **gateway** | 8080 | — | — |
| **auth-service** | 9001 | Postgres | 5433 |
| **product-catalog-service** | 9002 | Postgres | 5434 |
| **inventory-service** | 9003 | Postgres | 5435 |
| **order-service** | 9004 | Postgres | 5436 |
| **search-service** | 9005 | MongoDB | 27018 |
| **image-service** | 9006 | MongoDB | 27019 |
| **notification-service** | 9007 | Postgres | 5437 |
| **billing-service** | 9008 | Postgres | 5438 |

# ✅ **Infra Ports Table**

| Infra Component | Port |
| --- | --- |
| **Kafka** | 9092 |
| **Zookeeper** | 2181 |
| **Zipkin** | 9411 |
| **Prometheus** | 9090 |
| **Grafana** | 3000 |
