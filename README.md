# Personal Finance Manager Backend API

## Project Overview

This project is a backend application built using Spring Boot that provides APIs for managing personal finances. It supports user authentication, transaction management, category management, savings goals tracking, and financial reports.

The system follows a layered architecture and implements proper exception handling, validation, and unit testing as required for the Backend Intern assignment.


## Technology Stack

* Java 17
* Spring Boot 3
* Spring Security (Session-based authentication)
* Spring Data JPA
* H2 Database (for local development)
* Maven
* JUnit and Mockito for testing


## Setup Instructions

### Prerequisites

* Java 17 installed
* Maven installed
* IntelliJ IDEA or any Java IDE

### Steps to Run Locally

1. Clone the repository
2. Open project in IntelliJ IDEA
3. Run the application

Command:
mvn spring-boot:run

Application will start at:
http://localhost:8080

### H2 Database Console

http://localhost:8080/h2-console

JDBC URL:
jdbc:h2:mem:finance_db
Username: sa
Password: (leave empty)


## Authentication APIs

### Register User

POST /api/auth/register

Request Body:
{
"username": "[user@gmail.com](mailto:user@gmail.com)",
"password": "123",
"fullName": "Test User",
"phoneNumber": "9999999999"
}

### Login User

POST /api/auth/login

Request Body:
{
"username": "[user@gmail.com](mailto:user@gmail.com)",
"password": "123"
}

After login, a session cookie is generated and must be used for authenticated APIs.

---

## Category APIs

GET /api/categories
Fetch all default and custom categories

POST /api/categories
Create a custom category

Request Body:
{
"name": "Freelance",
"type": "INCOME"
}

DELETE /api/categories/{name}
Delete a custom category


## Transaction APIs

POST /api/transactions
Create transaction

GET /api/transactions
Get all user transactions

PUT /api/transactions/{id}
Update transaction

DELETE /api/transactions/{id}
Delete transaction


## Savings Goal APIs

POST /api/goals
Create savings goal

GET /api/goals
Get goals with progress tracking

Progress is calculated as:
Total income minus total expense since goal start date.


## Reports APIs

Monthly Report:
GET /api/reports/monthly/{year}/{month}

Yearly Report:
GET /api/reports/yearly/{year}

Reports include:

* Total income by category
* Total expenses by category
* Net savings


## Testing

Unit testing implemented using:

* JUnit
* Mockito

To run tests:
mvn test

Service layer testing implemented with mocked repositories to isolate business logic.



## Architecture

Layered architecture followed:

Controller Layer
Handles HTTP requests and responses.

Service Layer
Contains business logic and validations.

Repository Layer
Handles database interaction using Spring Data JPA.

DTO Pattern
Used for request and response separation from entities.

Global Exception Handling
Implemented using @ControllerAdvice to return proper HTTP status codes.


## Design Decisions

* Session-based authentication implemented using Spring Security.
* Custom exception classes used for proper HTTP status handling.
* Default categories provided for income and expense tracking.
* Transactions are permanently deleted to ensure reports remain accurate.
* H2 database used for simplicity and quick setup.


## Future Improvements

* JWT-based authentication
* Pagination and sorting
* Docker deployment
* Frontend integration
* Advanced analytics and charts


## Author

Jiwanshu
