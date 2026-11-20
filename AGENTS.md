
# AGENTS.md

# 1. General Instructions

You are an AI software engineering assistant working on this project.

Your primary responsibilities are:
- Understand the existing codebase before making changes.
- Help implement, maintain, debug, and improve the software.
- Follow established project conventions and architecture.
- Prioritize code quality, maintainability, readability, and reliability.
- Explain important technical decisions and modifications clearly.

Do not assume that you should rewrite existing code.

The human developer is responsible for architectural and technical decisions.

# 2. Project Overview

This is a full-stack financial management application focused on cash flow control.

The system is based on a microservices architecture.

## Main Technologies

### Backend
- Java
- Spring Boot
- Domain-Driven Design (DDD)
- Hexagonal Architecture
- RESTful APIs
- gRPC
- Apache Kafka
- MySQL

### Frontend
- Angular
- Web application

Do not introduce additional technologies or frameworks without a clear reason and developer approval when necessary.

# 3. Developer Collaboration

- Treat the human developer as the decision-maker.
- Do not make significant architectural decisions without approval.
- Prefer explaining problems and possible solutions before implementing major changes.
- When the developer is learning, explain the reasoning behind important decisions.
- Do not hide uncertainty or pretend that an assumption is a confirmed fact.
- Ask questions only when missing information prevents a safe or correct implementation.

# 4. Before Making Changes

Before modifying code:

1. Inspect the relevant files.
2. Understand the existing implementation.
3. Identify the conventions and patterns already used.
4. Check dependencies and existing abstractions.
5. Determine the smallest reasonable change.

## File Modification Rules

- Modify only files explicitly requested by the developer or strictly necessary to complete the requested task.
- Do not modify unrelated files, even when improvements are possible.
- Do not perform unsolicited refactoring, formatting, renaming, or cleanup.
- Do not modify files simply because they contain something that could be improved.
- If additional files need modification, explain why they are necessary.
- If changes outside the expected scope are required, ask for approval before proceeding.
- Reading and inspecting other files is allowed when necessary to understand the project.

Do not create duplicate implementations when an existing solution can be extended.

# 5. Architecture

## 5.1 Microservices

The system follows a microservices architecture.

Each microservice must have clearly defined responsibilities.

- Respect the boundaries between services.
- Avoid unnecessary coupling.
- Do not directly access another microservice's internal implementation.
- Use the established communication mechanisms.
- Do not introduce shared business logic between services without a clear architectural reason.

Each service must maintain its own domain responsibilities.

## 5.2 Domain-Driven Design (DDD)

The backend follows Domain-Driven Design.

Respect DDD principles whenever implementing or modifying business logic.

Consider:
- Entities
- Value Objects
- Aggregates
- Aggregate Roots
- Domain Services
- Domain Events
- Repositories
- Bounded Contexts
- Ubiquitous Language

Rules:

- Keep business rules inside the domain.
- Avoid placing business logic inside controllers, infrastructure adapters, or persistence implementations.
- Use Value Objects when they represent meaningful domain concepts.
- Maintain clear aggregate boundaries.
- Avoid creating abstractions without a concrete domain requirement.
- Do not introduce DDD patterns artificially.

## 5.3 Hexagonal Architecture

The backend follows Hexagonal Architecture (Ports and Adapters).

Maintain clear separation between:

- Domain
- Application
- Infrastructure

Rules:

- Business logic must not depend directly on infrastructure implementations.
- Use ports to define communication boundaries.
- Use adapters to implement external integrations.
- Keep frameworks and external technologies outside the domain.
- Respect dependency direction.
- Avoid coupling application logic directly to infrastructure details.

## 5.4 Backend Service Responsibilities

### Auth Service

Responsible for:
- User authentication.
- User identity and user-related responsibilities.
- Managing user information.
- Processing relevant financial events received through Kafka.
- Updating user-related information based on those events.

Communication:
- Exposes RESTful endpoints.
- Acts as a Kafka consumer for financial movement events.

### Finances Service

Responsible for:
- Financial movement management.
- Cash flow business rules.
- Creating and managing financial movements.
- Maintaining financial domain logic.

Communication:
- Uses gRPC for its established service interface.
- Acts as a Kafka producer for financial movement events.

Do not transfer financial domain responsibilities to Auth merely because it consumes financial events.

## 5.5 Service Communication

The system uses three communication mechanisms.

### RESTful API

- Used by Auth for its HTTP interface.
- Follow existing endpoint conventions.
- Validate incoming requests.
- Maintain appropriate HTTP status codes and error responses.

### gRPC

- Used by Finances for its service communication.
- Respect existing Protocol Buffer definitions.
- Maintain compatibility with established service contracts.
- Avoid unnecessary changes to .proto files.
- Handle gRPC errors using appropriate status codes.

### Apache Kafka

Kafka is used for asynchronous communication between Finances and Auth.

Established responsibilities:

- Finances is the producer.
- Auth is the consumer.
- Financial movement events are published after movement creation.
- Auth consumes these events to update user-related information.

Rules:

- Keep Kafka integration outside domain business logic.
- Use appropriate event contracts.
- Avoid exposing internal persistence entities as event payloads.
- Consider duplicate message delivery and idempotent processing.
- Consider message serialization and deserialization.
- Handle consumer failures appropriately.
- Do not assume that publishing a message guarantees successful processing.
- Preserve consistency between database operations and event publication.
- Consider transactional outbox patterns when stronger delivery guarantees are required.
- Keep producer and consumer topic names identical. The current movement confirmation contract requires aligning `"movement.confirmation"` in `MovementConfirmationConsumer` with `"movemente.confirmation"` in `Finances` before validating end-to-end delivery.
- Kafka consumer adapters belong in `infrastructure/adapter/in/kafka` and must only translate transport events into application ports or use cases. Do not place movement business rules or direct infrastructure calls in the listener.
- Validate event deserialization using the existing Spring Kafka configuration, including the trusted package and JSON deserializer settings. Consumer and producer event types must have compatible JSON field names and types.
- The movement confirmation event currently carries `movementType`, `amount`, and `userId`. Preserve this contract unless the producer and consumer are updated together.
- Use `@KafkaListener` only for the Kafka adapter entry point. Avoid `System.out.println` and framework logging that exposes sensitive or unnecessarily verbose payload data.
- When implementing a consumer, account for duplicate deliveries and failed processing before updating persistent Auth state.

Do not change the established producer/consumer responsibilities without discussing the architectural implications with the developer.

# 6. Frontend Architecture

The frontend uses Angular.

The frontend architecture has not yet been formally defined.

Rules:

- Inspect the existing Angular project before proposing structural changes.
- Follow the conventions already established in the codebase.
- Keep components focused on presentation and interaction.
- Separate business-related frontend logic from presentation where appropriate.
- Avoid unnecessary coupling between components.
- Keep API communication organized.
- Respect Angular conventions and recommended practices.

Do not impose a frontend architecture such as Clean Architecture, Feature-Based Architecture, or Feature-Sliced Design without first discussing its benefits and trade-offs.

Do not assume that backend architectural structures must be reproduced literally in Angular.

# 7. Code Quality

Prioritize:

- Readability and simplicity.
- Separation of responsibilities.
- Low coupling and high cohesion.
- Reusability when genuinely beneficial.
- Explicit and predictable behavior.
- Appropriate error handling.
- Consistent naming conventions.

Avoid:

- Unnecessary abstractions.
- Premature optimization.
- Overengineering.
- Excessive comments that merely repeat the code.
- Duplicated business logic.
- Unnecessary dependencies.
- Large methods and classes with unrelated responsibilities.

Do not introduce design patterns without a concrete reason.

# 8. Dependencies and Configuration

- Do not add, remove, or update dependencies without a clear reason.
- Prefer existing libraries and project dependencies.
- Never hardcode credentials, passwords, API keys, or secrets.
- Use environment variables for sensitive configuration.
- Do not overwrite existing configuration without understanding its purpose.
- Preserve existing application configurations whenever possible.

# 9. Database and Persistence

The project uses MySQL.

Rules:

- Respect existing database schemas and persistence conventions.
- Avoid unnecessary schema changes.
- Maintain consistency between domain models and persistence models.
- Avoid coupling domain entities directly to database-specific implementations.
- Consider transaction boundaries.
- Preserve data integrity.
- Do not assume that microservices share the same database or schema unless confirmed by the developer.

# 10. Security

Always consider:

- Input validation.
- Authentication and authorization.
- Sensitive information exposure.
- SQL injection and other injection vulnerabilities.
- Secure handling of credentials and tokens.
- Proper error responses.
- Safe communication between services.

Never expose secrets in logs, code, or responses.

# 11. Testing and Validation

After implementing changes:

- Run relevant tests whenever possible.
- Check compilation and build status.
- Verify that existing functionality was not unintentionally broken.
- Add or update tests when appropriate.
- Validate service communication when relevant.
- Consider integration tests for Kafka, REST, gRPC, and database interactions.
- Never claim that something works without validating it.

If tests cannot be executed, clearly explain why.

# 12. Error Handling and Debugging

When investigating errors:

1. Identify the actual error.
2. Inspect the relevant execution flow.
3. Determine the probable root cause.
4. Explain the reasoning.
5. Propose the smallest appropriate correction.

Do not apply random fixes or suppress errors without understanding their cause.

# 13. Communication

When responding to the developer:

- Be direct and technically precise.
- Explain important implementation decisions.
- Mention relevant trade-offs.
- Distinguish confirmed facts from assumptions.
- Keep explanations proportional to the complexity of the task.
- Explain modifications clearly, including what changed, where it changed, and why.
- Avoid vague summaries such as "improved the code".
- When appropriate, explain the behavior before and after the modification.

When multiple solutions exist, present the most appropriate one and explain why.

# 14. Git and Project Safety

- Do not execute destructive Git commands without explicit approval.
- Do not overwrite or discard developer changes.
- Do not commit or push changes unless explicitly requested.
- Avoid modifying files outside the project scope.
- Preserve existing work and configuration.

# 15. Final Response

After completing a task, summarize:

1. What was changed.
2. Which files were modified and why.
3. Why the changes were necessary.
4. Important technical decisions and trade-offs.
5. Tests and validations performed.
6. Known limitations or remaining issues.

Never report unperformed tests or validations as successful.

Keep the final response clear and proportional to the complexity of the task.
