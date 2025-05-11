# 🏥 Healthcare Microservices System

This project is a **comprehensive healthcare microservices architecture** built with modern backend technologies like **Spring Boot**, **gRPC**, **Kafka**, **Docker**, and **AWS**. The system includes multiple services such as **Patient Management**, **Billing**, **Analytics**, **Authentication**, and an **API Gateway**, designed for scalability, observability, and production-readiness.

---

## 📌 Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Services Overview](#services-overview)
- [Setup Instructions](#setup-instructions)
- [Docker & Deployment](#docker--deployment)
- [Testing](#testing)
- [AWS Infrastructure](#aws-infrastructure)
- [Screenshots](#screenshots)
- [Credits](#credits)

---

## ✅ Features

- CRUD operations for patients with proper validation and DTO mapping
- Custom exception handling and centralized error responses
- gRPC-based communication between services (Patient ↔ Billing)
- Kafka-based messaging for real-time analytics
- Secure authentication using JWT and Spring Security
- API Gateway integration for centralized routing and security
- Containerized services with Docker and deployed to AWS using CloudFormation
- Local development via LocalStack (AWS emulation)
- Comprehensive integration and unit testing

---

## 🧱 Architecture

```plaintext
[API Gateway]
     |
     v
[Auth Service] <-- JWT
     |
     v
[Patient Service] <--> [Billing Service via gRPC]
     |
     v
[Kafka Producer] --> [Analytics Service]
⚙️ Tech Stack
Spring Boot (Java)

gRPC for inter-service communication

Apache Kafka for event-driven messaging

Docker for containerization

PostgreSQL as the database

AWS ECS, MSK, RDS, VPC for deployment

CloudFormation for Infrastructure as Code

JWT + Spring Security for auth

LocalStack for local AWS emulation

JUnit & Postman for testing

🧩 Services Overview
Service	Description
Patient Service	Manages patient data, validation, and lifecycle
Billing Service	Exposes gRPC endpoints for billing operations
Analytics Service	Consumes Kafka messages and processes analytics
Auth Service	Handles user login, password encoding, and JWT
API Gateway	Routes requests, validates tokens, and documents APIs

🚀 Setup Instructions
1. Clone the repository
bash
Copy
Edit
git clone https://github.com/your-username/healthcare-microservices.git
cd healthcare-microservices
2. Configure Environment
Create .env or use preconfigured application.yml files for each service.

3. Start Services with Docker
bash
Copy
Edit
docker-compose up --build
4. Access Local Services
API Gateway: http://localhost:8080

Swagger Docs: http://localhost:8080/swagger-ui.html

🐳 Docker & Deployment
To Build and Run Individually:
bash
Copy
Edit
cd patient-service
docker build -t patient-service .
docker run -p 8081:8081 patient-service
Deploy to LocalStack:
bash
Copy
Edit
cd infrastructure
aws cloudformation deploy --template-file template.yaml --stack-name healthcare-stack --endpoint-url=http://localhost:4566
🧪 Testing
Integration tests: ./gradlew test or mvn test

Test login and secure endpoints using Postman collections

Kafka and gRPC tested using embedded services and mocks

☁️ AWS Infrastructure
Provisioned via CloudFormation:

VPC

RDS for Patient, Auth, Billing services

MSK (Kafka Broker)

ECS Cluster for deploying Docker containers

Application Load Balancer

📸 Screenshots
(Optional: Add screenshots of Swagger UI, Postman, Docker Dashboard, AWS Console, etc.)

🙌 Credits
This project was built by following the amazing tutorial:
📺 YouTube - Spring Boot Microservices

📄 License
This project is licensed under the MIT License. See LICENSE file for details.

yaml
Copy
Edit

---

Would you like this as a downloadable `.md` file or should I help you write a `.docker-compose.yml` for easier s
