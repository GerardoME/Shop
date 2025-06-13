------

# Adapters Module

------

## Purpose

The **`adapters` module** acts as the **outermost layer** in the hexagonal architecture. Its primary responsibility is **interacting with the outside world**, adapting domain interfaces (ports) to the specific technologies being used. It contains the implementations of "adapters" that connect the **application's core** (the `domain` and `application` modules) with external elements like databases, messaging systems, external APIs, web frameworks (REST), and other services.

## Responsibilities

* **Port Implementations (Drivers and Driven)**:
    * **Driver Adapters (Inbound)**: These manage inputs to the system. For example, REST controllers that convert HTTP requests into application commands.
    * **Driven Adapters (Outbound)**: These manage outputs from the system. For instance, repository implementations that persist data to a database, or HTTP clients that call other APIs.
* **Data Mapping**: It contains the mechanisms for transforming data between domain formats and technology-specific formats (e.g., DTOs for REST, JPA entities for the database). It uses the **MapStruct library** to achieve this goal.
* **Technology-Specific Configuration**: This includes configurations related to the database.

## Dependencies

This module depends on the **`domain` module** and the **`application` module** (if adapters need to directly invoke application services).