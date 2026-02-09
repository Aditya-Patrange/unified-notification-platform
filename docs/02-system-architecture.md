# System Architecture

## Architecture Style
The system follows a microservices-based architecture where each service is
independently deployable, loosely coupled, and owns its own database schema.

An API Gateway acts as the single entry point for all client requests.

---

## High-Level Components

### 1. API Gateway
- Serves as the single entry point for all external clients
- Handles request routing to backend services
- Performs authentication validation
- Enables centralized cross-cutting concerns

### 2. Auth Service
- Manages user authentication and authorization
- Issues JWT tokens upon successful login
- Maintains user and role information
- Acts as the identity provider for the system

### 3. Core Workflow Service
- Handles business workflows such as request submission and approvals
- Maintains request lifecycle and approval history
- Applies business rules for status transitions

### 4. Notification Service
- Manages in-app notifications
- Tracks read, unread, and expired notifications
- Generates notifications based on system events

---

## Database Strategy
Each microservice owns its own database schema.
No other service is allowed to directly access another service’s database.

This ensures:
- Strong service boundaries
- Independent scaling
- Reduced coupling between services

---

## Communication Between Services

### Phase 1 (Synchronous)
- REST-based communication using HTTP
- Services communicate using well-defined APIs

### Phase 2 (Asynchronous – Optional)
- Event-driven communication using Kafka
- Events such as REQUEST_CREATED or STATUS_UPDATED
  trigger notification processing

---

## Request Flow (High Level)

1. Client sends request to API Gateway
2. API Gateway validates JWT token
3. Request is routed to appropriate backend service
4. Core Workflow Service processes business logic
5. Notification Service is triggered based on events
6. Response is returned via API Gateway

---

## Security Considerations
- JWT-based authentication
- Role-based access control
- No direct service-to-database access
- Centralized authentication via Auth Service

