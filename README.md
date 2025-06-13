------

# Price Rate API

This project is a Spring Boot application developed with an **API-first methodology** and following a **Hexagonal Architecture**. It uses an **H2 in-memory database** for data persistence and **Maven** for build automation. The application includes both **unit tests (JUnit)** and **integration tests (Cucumber)**, and it's fully **Dockerized** for easy deployment.

------



## Design Decisions

Several key design decisions were made to ensure the project is robust, maintainable, and scalable:

* **API-First Approach**: The API contract was defined first using OpenAPI (Swagger) specifications. This ensures a clear and consistent interface, facilitates parallel development between frontend and backend teams, and improves documentation accuracy.

* **Hexagonal Architecture (Ports and Adapters)**: This architectural style separates the core business logic (domain) from external concerns like databases, UI, and third-party services.
    * **Ports**: Define the interfaces through which the application interacts with the outside world.
    
    * **Adapters**: Implement these ports, providing concrete implementations for interacting with specific technologies
    
      
    

  This design promotes:
  
    * **Testability**: The core domain can be tested in isolation without needing external dependencies.
    
    * **Maintainability**: Changes in external technologies (e.g., switching from H2 to PostgreSQL) require modifications only in the adapter layer, leaving the core business logic untouched.
    
    * **Flexibility**: New adapters can be easily added to support different external systems.

* **H2 Database**: Chosen for its simplicity and ease of use in development and testing environments. As an in-memory database, it provides quick startup times and no external setup is required.

* **Maven**: Used for dependency management, project compilation, and build lifecycle management, ensuring a standardized build process.

* **JUnit for Unit Tests**: Used for testing individual components and business logic in isolation, ensuring the correctness of core functionalities.

* **Cucumber for Integration Tests**: Enables behavior-driven development (BDD) by allowing tests to be written in a human-readable Gherkin syntax, making them accessible to non-technical stakeholders and providing clear documentation of system behavior.

* **Dockerization**: The application is containerized to provide a consistent and isolated environment for development, testing, and production, simplifying deployment and ensuring portability.

---

## Getting Started

### Prerequisites

* Java 17 or higher
* Maven 3.x
* Docker Desktop (if running via Docker)

### Build and Run Commands

#### 1. Compile the Project

To compile the project and package it into a JAR file, use Maven:

```bash
mvn clean install
```

#### 2. Run Unit Tests

To execute only the unit tests:

```bash
mvn test
```

#### 3. Run Integration Tests (Cucumber)

To run the integration tests using Cucumber:

```bash
mvn verify -Pintegration-tests
```

#### 4. Run the Application (without Docker)

After compilation, you can run the Spring Boot application directly:

```bash
java -jar bootstrap/target/bootstrap-0.0.1-SNAPSHOT.jar
```

#### 5. Build and Run with Docker

First, build the Docker image:

```bash
docker build -t price-rate-api .
```

Then, run the Docker container:

```bash
docker run -p 8080:8080 price-rate-api
```

This will map port 8080 of your host machine to port 8080 inside the Docker container.

---

## Principal URLs

Once the application is running, you can access the following URLs:

* **Swagger UI (API Documentation)**:
    ```
    http://localhost:8080/swagger-ui.html
    ```
  This provides interactive API documentation generated from the OpenAPI specification, allowing you to explore and test endpoints.
* **H2 Console (Database Management)**:
    ```
    http://localhost:8080/h2-console
    ```
    * **JDBC URL**: `jdbc:h2:mem:testdb` (or whatever you've configured in `application.properties`)
    * **Username**: `sa` (default)
    * **Password**: (leave blank, default)
      This console allows you to inspect the H2 database content and execute SQL queries.
* **Docker Status (List Running Containers)**:
  
    ```bash
    docker ps
  ```
  This command, executed in your terminal, will list all running Docker containers, allowing you to verify that `price-rate-api` container is up and running.

---