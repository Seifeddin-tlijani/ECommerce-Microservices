# Microservices E-Commerce Application

## 1. Purpose

The **Microservices E-Commerce Application** is designed to manage e-commerce operations using a microservices architecture. This application allows users to browse products, place orders, manage inventory, in a scalable and efficient manner.

## 2. Scope

The application is built with a **modular** microservices architecture, allowing different services to operate independently and communicate through APIs. Each service handles a specific business function, ensuring flexibility, scalability, and ease of maintenance.

## 3. Architecture

The application uses the following microservices:
- **Product Service**: Handles product catalog operations such as listing, adding, and updating products.
- **Order Service**: Manages order creation, order status updates, and order history.
- **Inventory Service**: Monitors and updates inventory levels for products.
- **Discovery Service**:Implemented Eureka Server to enable service discovery across microservices.
- **API Gateway**: Routes client requests to the appropriate microservices and acts as a unified entry point for the system.

## 4. Authentication and Authorization

To secure the microservices, the application integrates Keycloak, an open-source Identity and Access Management (IAM) solution. Keycloak provides a robust way to handle user authentication and authorization, enabling features such as:
- **Single Sign-On (SSO)**: Users can log in once and gain access to all services without needing to authenticate repeatedly.
- **Role-Based Access Control (RBAC)**: Assign roles to users for granular access management across different services.
- **OAuth2 and OpenID Connect Support**: Easily integrates with OAuth2 and OpenID Connect for secure API access.

## Keycloak Setup
* **Run Keycloak**: Start the Keycloak server using Docker:
   ```bash
   docker run -p 8080:8080 \
   -e KC_BOOTSTRAP_ADMIN_USERNAME=admin \
   -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin \
   -v D:/data/keycloak:/opt/keycloak/data/h2 \
   quay.io/keycloak/keycloak:26.0.0 start-dev

## 5. Technologies Used

The application is developed using a combination of technologies, including but not limited to:

- **Spring Boot**: For building microservices.
- **Spring Cloud**: For cloud-native development, including service discovery and configuration management.
- **Docker**: For containerizing services and ensuring consistency across different environments.
- **PostgreSQL/MySQL**: For persistent data storage.
- **Resilience4j**: For implementing fault tolerance and resilience patterns.


