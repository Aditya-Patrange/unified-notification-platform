# Unified Notification & Approval Platform

An enterprise-grade microservices-based platform to manage approval workflows
and deliver event-driven notifications to users.

## Overview
This project is designed to simulate a real-world internal enterprise system
used for handling approval requests and notifications across applications.

The system follows a microservices architecture with clear service boundaries
and independent databases per service.

## Architecture (High Level)
- API Gateway – Single entry point for all client requests
- Auth Service – Authentication, authorization, JWT handling
- Core Workflow Service – Request submission and approval lifecycle
- Notification Service – In-app notification management

## Tech Stack
### Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Cloud Gateway
- JPA / Hibernate

### Database
- PostgreSQL (separate schema per service)

### DevOps
- Docker
- Docker Compose
- Git & GitHub

## Repository Structure
