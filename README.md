# Mall Application

## Introduction
The application leverages the power of microservices and modern tools for scalability, performance, and maintainability.

---

### Table of Contents
I. [Architecture Overview](#architecture-overview)

II. [Technologies Used](#technologies-used)

III. [Microservices Description](#microservices-description)

IV. [Setup and Installation](#setup-and-installation)

---

<div id="architecture-overview"></div>

## I. Architecture Overview

---

## II. Technologies Used

| Technology            | Purpose                          |
|-----------------------|----------------------------------|
| **Spring Boot 3**     | Backend framework for services  |
| **Kafka**             | Asynchronous messaging system   |
| **Brevo**             | Email and notification service  |
| **mongoDB**           | NoSQL database for flexible and scalable data storage|
| **MySQL**             | Relational database (exam data) |
| **Docker**            | Containerization and deployment |
| **Swagger/OpenAPI**   | API documentation               |

---

## III. Microservices Description

### 1. Gateway
- **Purpose**: Routes external requests to appropriate microservices.
- **Technology**: Spring Cloud Gateway
- **Key Features**:
    - Centralized entry point for all requests.
    - Dynamic routing to microservices.
    - Integrated with Swagger UI for API documentation.

---

## IV. Setup and Installation

### Prerequisites
- **JDK 17**
- **Kafka**
- **mySQL**
- **mongoDB**
- **redis**

### Steps to Run the Application
1. **Clone the repository**:
   ```bash
   git https://github.com/huynhanx03/xmall.git
   cd xmall
   ```
---

## Contributors
- **huynhanx03** - [GitHub](https://github.com/huynhanx03)

---

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

