# spring-boot-rbac

[![CI](https://github.com/hendisantika/spring-boot-rbac/actions/workflows/ci.yml/badge.svg)](https://github.com/hendisantika/spring-boot-rbac/actions/workflows/ci.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.16-6DB33F?logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-336791?logo=postgresql)
![Dependabot](https://img.shields.io/badge/Dependabot-enabled-025E8C?logo=dependabot)

Role-Based Access Control (RBAC) demo built with Spring Boot and Spring Security. Users get **roles**, roles bundle
fine-grained **permissions** (e.g. `order:read`), and endpoints are protected by role checks and
`@PreAuthorize` permission checks.

## Tech Stack

- Java 25
- Spring Boot 3.5.16 (Web, Security, Data JPA, Validation, Docker Compose support)
- PostgreSQL 17
- Lombok
- Maven 3.9.16 (via Maven Wrapper)

## Requirements

- JDK 25
- Docker & Docker Compose (for PostgreSQL)

## Getting Started

```shell
git clone https://github.com/hendisantika/spring-boot-rbac.git
cd spring-boot-rbac
./mvnw spring-boot:run
```

Thanks to `spring-boot-docker-compose`, running the app automatically starts the PostgreSQL container defined in
[`compose.yaml`](compose.yaml) (host port `5434`). To start the database manually instead:

```shell
docker compose up -d
```

The application runs at `http://localhost:8080`.

### Build & Test

Tests need a running PostgreSQL on `localhost:5434` (database `rbac_demo`, user `yu71`, password `53cret`):

```shell
docker run -d --rm --name rbac_pg -p 5434:5432 \
  -e POSTGRES_USER=yu71 -e POSTGRES_PASSWORD=53cret -e POSTGRES_DB=rbac_demo \
  postgres:17.4-alpine3.21

./mvnw verify
```

## Roles & Permissions

Seed data is created at startup by `DataInitializer`.

| Role      | Permissions                                                                          |
|-----------|--------------------------------------------------------------------------------------|
| `ADMIN`   | `user:*`, `product:*`, `order:*` (read, write, delete)                               |
| `MANAGER` | `user:read`, `user:write`, `product:read`, `product:write`, `order:read`, `order:write` |
| `USER`    | `user:read`, `product:read`, `order:read`                                            |

Default users (password `password` for all):

| Username  | Role      |
|-----------|-----------|
| `admin`   | `ADMIN`   |
| `manager` | `MANAGER` |
| `user`    | `USER`    |

## API Endpoints

Authentication uses HTTP Basic.

| Method   | Endpoint                                   | Access                 |
|----------|--------------------------------------------|------------------------|
| `POST`   | `/api/auth/register`                       | Public                 |
| `POST`   | `/api/auth/login`                          | Public                 |
| `GET`    | `/api/orders`                              | `order:read`           |
| `GET`    | `/api/orders/{id}`                         | `order:read`           |
| `POST`   | `/api/orders/add`                          | `order:write`          |
| `PUT`    | `/api/orders/{id}`                         | `order:write`          |
| `DELETE` | `/api/orders/delete/{id}`                  | `order:delete`         |
| `GET`    | `/api/admin/users`                         | Role `ADMIN`           |
| `POST`   | `/api/admin/users/{userId}/roles`          | Role `ADMIN`           |
| `DELETE` | `/api/admin/users/{userId}/roles/{roleName}` | Role `ADMIN`         |
| `DELETE` | `/api/admin/users/{userId}`                | Role `ADMIN`           |

### Examples

```shell
# Register a new user
curl -X POST http://localhost:8080/api/auth/register \
  -H 'Content-Type: application/json' \
  -d '{"username":"john","password":"secret123"}'

# Read orders as a regular user
curl -u user:password http://localhost:8080/api/orders

# Delete an order (admin only, user gets 403)
curl -u admin:password -X DELETE http://localhost:8080/api/orders/delete/1

# Assign a role to a user
curl -u admin:password -X POST http://localhost:8080/api/admin/users/4/roles \
  -H 'Content-Type: application/json' \
  -d '{"roleName":"MANAGER"}'
```

## CI/CD

- **GitHub Actions** ([`.github/workflows/ci.yml`](.github/workflows/ci.yml)): builds and tests on Temurin JDK 25 with
  a PostgreSQL 17 service container on every push and pull request to `main`.
- **Dependabot** ([`.github/dependabot.yml`](.github/dependabot.yml)): weekly grouped updates for Maven dependencies,
  GitHub Actions, and Docker Compose images, using Conventional Commit prefixes. Spring Boot major upgrades are
  ignored and handled manually.

## Author

Hendi Santika — [hendisantika](https://github.com/hendisantika)
