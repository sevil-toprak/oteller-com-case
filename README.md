# 🏨 Hotel Reservation Microservice System

## 📚 Versions

- **Spring Boot**: 3.4.4
- **Java**: 21
- **PostgreSQL**: 17
- **Docker**: ✅

---

### 🧩Services

| Service Name         | Description                                                    | Port   |
|----------------------|----------------------------------------------------------------|--------|
| API Gateway          | Api Gateway, manage routing services.                          | 8080   |
| User Service         | Generate JWT token.                                            | 8084   |
| Hotel Service        | Hotel and Room crud operations.                                | 8081   |
| Reservation Service  | Reservation crud operations and Kafka producer implementation. | 8083   |
| Notification Service | Kafka consumer implementation and logging.                     | 8082   |

---

## 🐳 Clone the Repository and Docker run
Each microservice has its own Dockerfile and docker-compose.yml for isolated builds and deployments.
However, for faster and easier local development, you can simply run the docker-compose.yml located in the project’s root directory:
```bash
git clone https://github.com/sevil-toprak/oteller-com-case.git
cd otellercom-case
docker-compose up --build
```

---


