------

# Bootstrap Module

------

## Purpose

The **`bootstrap` module** is the application's **entry point**. Its main function is to assemble and configure all application and infrastructure components, allowing the application to start and run. This is the layer where Spring Boot configuration is defined.

## Responsibilities

- **Spring Boot Configuration**: Contains the application's `main` class and all Spring `@Configuration` setups (e.g., `@SpringBootApplication`, `@EnableJpaRepositories`, `@ComponentScan`).
- **Application Properties**: Houses configuration files (`application.properties`, `application.yml`) that define environment settings.
- **Runtime Environment**: Responsible for spinning up the embedded web server, configuring the database connection, and any other infrastructure components needed for the application to be operational.
- **Integration Tests**: Contains integration tests that require the entire application to be running (Cucumber tests).

## Dependencies

This module depends on **all other modules** in the project (`domain`, `application`, `adapters`), as it needs them to assemble the complete application.
