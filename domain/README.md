------

# Domain Module

------

## Purpose

The **`domain` module** contains the domain entities that represent the business. It **doesn't contain core business logic**, which is implemented in the `application` module's services. It's the "core" of the hexagonal architecture and should be completely independent of any external technology or infrastructure details.

## Responsibilities

* **Domain Entities / Aggregates**: Defines the objects that represent business information and behavior.

## Dependencies

This module has **no dependencies on any other project modules** or infrastructure libraries like databases, web frameworks, or messaging. Its only dependencies should be standard Java libraries or general-purpose libraries that introduce no technological bias (e.g., collections, basic utilities). This ensures its purity and reusability.

---