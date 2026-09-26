# Java Code Assignment

This project is a Java and Quarkus based code assignment covering REST API development, persistence, business validations, transaction handling, testing, and API documentation.

## About the assignment

The tasks and requirements for this assignment are described in:

- [CODE_ASSIGNMENT](CODE_ASSIGNMENT.md)
- [CASE_STUDY](../case-study/CASE_STUDY.md)
- [QUESTIONS](QUESTIONS.md)

## Technology Stack

- Java 17
- Quarkus
- Maven
- PostgreSQL
- Hibernate ORM with Panache
- JAX-RS / REST
- JUnit 5
- REST Assured
- Docker

## Requirements

To build and run the application, you will need:

- JDK 17 or later
- Maven Wrapper included in the project
- Docker Desktop with Docker Compose support or a running PostgreSQL database

The project uses the Maven Wrapper, so Maven does not need to be installed separately.

## Database Setup

The application uses PostgreSQL.

A PostgreSQL container can be started using:

```sh
docker run --name quarkus-postgres -e POSTGRES_USER=quarkus_test -e POSTGRES_PASSWORD=quarkus_test -e POSTGRES_DB=quarkus_test -p 15432:5432 -d postgres:16