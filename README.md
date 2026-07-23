# Drone-Delivery-System

A Spring Boot Microservices-based **Drone Delivery System** designed to manage package deliveries using autonomous drones. The system registers packages, manages drones, calculates optimal delivery routes, assigns deliveries, and routes all client requests through a centralized API Gateway.

This project demonstrates the implementation of a distributed microservices architecture using **Spring Boot**, **Spring Data JPA**, **REST APIs**, **RestTemplate**, and **API Gateway**.

# Project Overview

The Drone Delivery System enables logistics companies and e-commerce platforms (Amazon, Flipkart, etc.) to automate package delivery using drones.

The system performs the following operations:

- Register customer packages
- Register and manage drones
- Search the best delivery route
- Find an available drone
- Assign packages to drones
- Start deliveries
- Complete deliveries
- Track delivery status

All client requests are routed through a centralized API Gateway.

# System Architecture

![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/Screenshot%202026-07-23%20140307.png)


# WORKFLOW
```
Register Package
       │
       ▼
Find Available Drone
       │
       ▼
Find Best Route
       │
       ▼
Assign Delivery
       │
       ▼
Update Package Status
       │
       ▼
Start Delivery
       │
       ▼
Complete Delivery
```

# TECHNOLOGY STACK
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/image_20c78c84.png)

# MICROSERVICES

# 1] PACKAGE SERVICE

# RESPONSIBILITIES

- Register packages
- Retrieve package details
- Update package status

# ENDPOINTS
```

 Method   Endpoint 

 POST     /packages/create 
 GET      /packages/{id} 
 PUT      /packages/{id}/status 

```
# 2]  DRONE SERVICE

 # RESPONSIBILITIES

- Register drones
- View all drones
- Find available drones
- Update drone status
- Delete drones

## ENDPOINTS
```
 Method  Endpoint 

 POST   /drones 
  GET   /drones 
  GET   /drones/{id} 
  GET   /drones/available 
  PUT   /drones/{id}/status 
 DELETE /drones/{id} 
```

## 3] ROUTE SERVICE

 ## Responsibilities

- Register routes
- Retrieve routes
- Update routes
- Delete routes
- Search the best route

## Endpoints
```
Method  Endpoint 

 POST  /routes/create 
 GET   /routes 
 GET  /routes/{id} 
 PUT  /routes/{id} 
 DELETE  /routes/{id} 
 GET  /routes/search 
```

# 4] DELIVERY SERVICE

## Responsibilities

- Assign deliveries
- Retrieve delivery details
- View all deliveries
- Start delivery
- Complete delivery
- Assign available drones to packages
- Track delivery progress

## Endpoints

```
Method     Endpoint

POST       /deliveries
GET        /deliveries
GET        /deliveries/{id}
PUT        /deliveries/{id}/start
PUT        /deliveries/{id}/complete
```

# API Gateway

The API Gateway acts as the single entry point for all client requests.

### Routes

```
/packages/**
/drones/**
/routes/**
/deliveries/**
```

All requests are forwarded to their respective microservices using **RestTemplate**.

## Inter-Service Communication

The project uses **RestTemplate** for communication between microservices.

## Implemented Communication

### 1. Delivery Service → Package Service

- Validate package before delivery assignment
- Update package delivery status

### 2. Delivery Service → Drone Service

- Find an available drone
- Reserve the selected drone
- Update drone status during delivery

### 3. Delivery Service → Route Service

- Find the optimal route between source and destination

### 4. API Gateway → All Services

- Routes incoming client requests to the corresponding microservice


# Delivery Process

```text
Customer Places Order
        │
        ▼
Package Registered
        │
        ▼
Find Available Drone
        │
        ▼
Find Best Route
        │
        ▼
Assign Delivery
        │
        ▼
Drone Starts Delivery
        │
        ▼
Package Delivered
        │
        ▼
Delivery Completed
```

---

#  Project Structure

```
Drone-Delivery-System
│
├── PackageService
│
├── DroneService
│
├── RouteService
│
├── DeliveryService
│
├── ApiGateway
│
└── README.md
```

---

# Database

Each microservice maintains its own independent MySQL database.

- Package Database
- Drone Database
- Route Database
- Delivery Database

This follows the **Database per Microservice** design pattern.

---

#  Running the Project

Start the services in the following order:

1. Package Service
2. Drone Service
3. Route Service
4. Delivery Service
5. API Gateway

Access all APIs through:

```
http://localhost:9090
```

Example:

```
POST http://localhost:9090/packages/create
```

#  Testing

All APIs were tested using **Postman**.

Example workflows tested:

- Register Package
- Register Drone
- Register Route
- Assign Delivery
- Find Available Drone
- Find Best Route
- Start Delivery
- Complete Delivery
- Retrieve Delivery Status

# Features Implemented

- REST APIs
- CRUD Operations
- Layered Architecture
- DTO Pattern
- Bean Validation
- Global Exception Handling
- Spring Data JPA
- Hibernate ORM
- MySQL Integration
- API Gateway
- RestTemplate Communication
- Independent Databases
- Package Management
- Drone Management
- Route Management
- Delivery Assignment
- Delivery Tracking
- Postman API Testing

#  Output (Postman Testing)
## GATEWAY
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/get_gateway.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/get_gateway2.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/get_gateway3.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/get_gateway4.png)

## DELIVERY
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/delivert_post.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/delivery_complete.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/delivery_get.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/delivery_post_exception.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/delivery_start.png)

## DRONE
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/drone_available.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/drone_get.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/drone_post.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/drone_delete.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/drone_put.png)

## PACKAGE
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/package_get.png)

## ROUTE
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/route_get.png)
![image](https://github.com/VGSAIRAIMA/Drone-Delivery-System/blob/main/route_search.png)

#  DEVELOPER

```
# V G Sairaima 
B.E. Computer Science and Engineering
Java | Spring Boot | Microservices | REST APIs | MySQL | Backend Development
```




