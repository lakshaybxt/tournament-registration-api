# Tournament Registration API

This is a Spring Boot-based RESTful API for managing tournaments and user registrations. It provides endpoints to create and fetch tournaments, and allows users to register for available tournaments securely.

---

## Features

- Create and list tournaments
- Register users for tournaments (with user identity validation)
- View all tournament registrations
- UUID-based identifiers
- DTO-layer abstraction
- Clean architecture using services and mappers

---

## Tech Stack

| Tool / Library      | Purpose                              |
|---------------------|--------------------------------------|
| Java 21             | Main programming language            |
| Spring Boot         | Web framework                        |
| Spring Data JPA     | ORM for database interaction         |
| Hibernate Validator | Input validation                     |
| MapStruct           | DTO ↔ Entity mapping                 |
| PostgreSQL          | Relational database                  |
| Docker              | Code & Dependencies containerization |
| Lombok              | Boilerplate code reduction           |
---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/tournament-registration-api.git
cd tournament-registration-api
```
## 2. Configure the Application

Update your `application.properties` or `application.yml` with the following placeholders.  
Replace them with your actual environment values or define them in your `.env` or system environment variables:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```
## 3. Configure Email Support

For email features (e.g., notifications, confirmations), configure your email credentials in `application.properties`:

```properties
spring.mail.username=${SUPPORT_EMAIL}
spring.mail.password=${APP_PASSWORD}  # This should be an App Password
```
> **Note:** The `APP_PASSWORD` should be generated from your Google account under  
> **Security > App passwords** (recommended instead of your main password).

---

## 4. Build and Run

Use the Maven wrapper to build and start the application:

```bash
./mvnw spring-boot:run
```
> **Ensure your database service is running** before starting the app.

---

## 5. Docker (PostgreSQL Setup)

If you want to spin up a local PostgreSQL instance using Docker, use the following `docker-compose` service snippet:

```yaml
services:
  db:
    image: postgres:15.5
    ports:
      - "5434:5432"
    restart: always
    environment:
      POSTGRES_PASSWORD: changemeinprod!
```
> This runs PostgreSQL on `localhost:5434`.  
> Make sure your `spring.datasource.url` in the application config matches accordingly:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5434/your_db_name
```

---

## Want to see more? 

For detailed API reference, request/response models, and usage examples,  
**See documentation here:**  
[Tournament Registration API Documentation](https://western-aluminum-170.notion.site/Tournament-Registration-API-Documentation-225e44bc5a7f80b6be3bfa79e00c73ef?pvs=73)
