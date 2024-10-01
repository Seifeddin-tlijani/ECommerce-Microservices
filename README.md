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

