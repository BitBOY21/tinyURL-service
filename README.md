# 🔗 TinyURL Scalable Microservice

![Java](https://img.shields.io/badge/Java-21-orange?style=flat-square&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.3-green?style=flat-square&logo=springboot)
![Docker](https://img.shields.io/badge/Docker-Compose-blue?style=flat-square&logo=docker)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-forestgreen?style=flat-square&logo=mongodb)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-336791?style=flat-square&logo=postgresql)
![Redis](https://img.shields.io/badge/Caching-Redis-red?style=flat-square&logo=redis)

A robust, full-stack URL shortening service designed for high performance, scalability, and ease of deployment. This project demonstrates a microservices-ready architecture using **Spring Boot**, containerized with **Docker**, and utilizes a **Hybrid Database** approach (SQL + NoSQL) alongside **Read-Aside Caching** for optimal performance.

## 🏗 System Architecture

The system is designed to handle high read traffic (redirections) vs. write traffic (shortening) efficiently.

```mermaid
graph TD
    subgraph Client_Layer [Client Layer]
        User((User / Browser))
    end

    subgraph Docker_Stack [Dockerized Environment]
        App[Spring Boot Application]
        
        subgraph Caching [Speed Layer]
            Cache[(Redis Cache)]
        end

        subgraph Data_Layer [Hybrid Database Layer]
            SQL[(Postgres: User Auth)]
            NoSQL[(MongoDB: URL Mappings)]
        end
    end

    %% Flow of Data
    User -->|1. REST Request| App
    App <-->|2. Read-Aside| Cache
    App -->|3. Persistence| NoSQL
    App -->|4. User Mgmt| SQL
    
    %% Style
    style App fill:#e1f5fe,stroke:#01579b,stroke-width:2px
    style Cache fill:#ffebee,stroke:#c62828
    style SQL fill:#e3f2fd,stroke:#1565c0
    style NoSQL fill:#e8f5e9,stroke:#2e7d32
```
## 🚀 Quick Start
```
docker-compose up --build
```
Service will be available at: http://localhost:8080.

## 🛠 Tech Stack
* **Backend:** Java 21, Spring Boot 3.2.3.
* **NoSQL:** MongoDB (Scalable URL storage).
* **SQL:** PostgreSQL (User management).
* **Cache:** Redis (Read-Aside strategy).
* **DevOps:** Docker & Docker Compose.

