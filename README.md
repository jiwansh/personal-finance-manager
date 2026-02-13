# Personal Finance Manager Backend API

## Project Overview

This project is a backend application built using Spring Boot that provides REST APIs for managing personal finances.
It supports user authentication, category management, transaction tracking, savings goals, and financial reports.

The application follows a layered architecture and includes validation, global exception handling, unit testing, and Docker-based deployment.

This project has been developed as part of a Backend Intern assignment.

## Tech Stack

Java 17
Spring Boot 3
Spring Security (Session-based authentication)
Spring Data JPA
H2 Database (local development)
Maven
JUnit and Mockito (Unit Testing)
Docker
Render (Deployment)

## Live Deployment

Base URL:
[https://personal-finance-manager-iz5m.onrender.com](https://personal-finance-manager-iz5m.onrender.com)

Note:
The application is deployed on Render free tier.
The first request may take 30–60 seconds to wake the server.

## Running Locally

### Prerequisites

Java 17 installed
Maven installed
IntelliJ IDEA or any Java IDE

### Steps

Clone repository:

git clone [https://github.com/jiwansh/personal-finance-manager.git](https://github.com/jiwansh/personal-finance-manager.git)

Open project in IDE and run:

mvn spring-boot:run

Application runs at:

[http://localhost:8080](http://localhost:8080)

## H2 Database Console (Local)

URL:
[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

JDBC URL:
jdbc:h2:mem:finance_db

Username: sa
Password: (leave empty)

## Authentication

The application uses session-based authentication with Spring Security.

After successful login, a session cookie (JSESSIONID) is generated.
All protected APIs must be called using the same session.

## API Testing

APIs can be tested using Postman or curl.

### Base URLs

Local:
[http://localhost:8080/api](http://localhost:8080/api)

Hosted:
[https://personal-finance-manager-iz5m.onrender.com/api](https://personal-finance-manager-iz5m.onrender.com/api)

## Authentication APIs

### Register User

POST ```/api/auth/register```

Request Body:
```json
{
  "username": "user@gmail.com",
  "password": "1234",
  "fullName": "Test User",
  "phoneNumber": "9999999999"
}
```

### Login User

POST ```/api/auth/login```

Request Body:
```json
{
"username": "user@gmail.com",
"password": "1234"
}
```


After login, session cookie will be used automatically by Postman.

## Category APIs

### Get Categories

GET ```/api/categories```

Returns default and custom categories.

### Create Category

POST ```/api/categories```

Request Body:
```json
{
"name": "Freelance",
"type": "INCOME"
}
```

### Delete Category

DELETE ```/api/categories/{name}```

## Transaction APIs

### Add Transaction

POST ```/api/transactions```
Request Body:
```json
{
"amount": 5000,
"date": "2024-06-15",
"category": "Salary",
"description": "Salary credited"
}
```
### Get All Transactions

GET ```/api/transactions```

### Update Transaction

PUT ```/api/transactions/{id}```

Request Body:
```json
{
"amount": 3000,
"category": "Food",
"description": "Updated entry"
}
```


### Delete Transaction

DELETE ```/api/transactions/{id}```

## Savings Goal APIs

### Create Goal

POST ```/api/goals```

Request Body:
```json
{
"goalName": "Buy Laptop",
"targetAmount": 100000,
"targetDate": "2026-12-31"
}
```


### Get Goals with Progress

GET ```/api/goals```

Progress is calculated as total income minus total expenses since goal start date.

## Reports APIs

### Monthly Report

GET ```/api/reports/monthly/{year}/{month}```

Example:
GET ```/api/reports/monthly/2026/2```

Returns:

* total income by category
* total expenses by category
* net savings

### Yearly Report

GET 
```/api/reports/yearly/{year}```

## Unit Testing

Unit tests are implemented using:

JUnit
Mockito

Service layer testing is done by mocking repository dependencies to isolate business logic.

Run tests using:

```mvn test```

Test coverage focuses on service layer validation and exception handling.

## Architecture

Layered architecture is followed to maintain separation of concerns and scalability.

- **Controller Layer**
    - Handles HTTP requests and responses
    - Validates input data
    - Sends structured API responses

- **Service Layer**
    - Contains core business logic
    - Performs validations and calculations
    - Coordinates between controller and repository layers

- **Repository Layer**
    - Handles database operations using Spring Data JPA
    - Performs CRUD operations
    - Executes custom queries for reports and calculations

- **DTO Pattern**
    - Separates request and response models from entities
    - Prevents direct exposure of database entities
    - Improves API structure and maintainability

- **Global Exception Handling**
    - Implemented using `@ControllerAdvice`
    - Returns proper HTTP status codes
    - Provides consistent error responses across APIs

## Deployment

* Application is containerized using Docker and deployed on Render.

* Docker is used to ensure consistent environment across systems and simplify deployment.

## Design Decisions

* Session-based authentication implemented using Spring Security.
* Custom exception classes used for proper HTTP status handling.
* Default categories provided for income and expense tracking.
* Transactions are permanently stored for accurate reporting.
* H2 database used for simplicity in assignment setup.
* Docker used for deployment consistency.

## Future Improvements

* JWT-based authentication
* Pagination and filtering
* PostgreSQL database integration
* Frontend integration (React)
* Advanced analytics and charts

