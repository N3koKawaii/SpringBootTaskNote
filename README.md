## 📝 About This Project

SpringBootTaskNote is a **backend REST API** built using **Spring Boot** for managing **Todos and Notes**.  
Think of it as a simple productivity app backend where users can:

- Register & login
- Create todos
- Add notes (todo-linked or standalone)
- Securely access only their own data

This project is mainly built for **learning and practice**.

---

## 🚀 Tech Stack

- Java 25
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- H2 (for testing)
- Maven

---

## 🔐 Authentication & Security (JWT)

This project uses **JWT-based authentication**, not session-based.

How it works (roughly lah):

1. User login with username + password
2. Backend generates a **JWT access token**
3. Client sends the token in every request:
4. `JwtAuthenticationFilter` will:
- Validate token
- Check expiry
- Check if token is revoked
- Set authentication into SecurityContext

No token = no access 🚫

---

## 🔄 Logout & Token Revocation

This project supports:
- **Logout (single token)**
- **Logout from all devices**

How?
- Every JWT has a `jti` (unique ID)
- Revoked tokens are stored in database
- Each request checks if token already revoked

So even if token not expired, once revoked → cannot use already.

---

## 🧠 Authorization & User Isolation

- Every Todo and Note belongs to **one user**
- Backend always checks **current logged-in user**
- Cannot access or modify other people data (should be safe 👍)

---

## 📦 API Overview (Simple)

### User
- `POST /users/register`
- `POST /users/login`
- `POST /users/logout`
- `POST /users/logout-all`

### Todo
- `GET /todos`
- `POST /todos`
- `PUT /todos/{id}`
- `DELETE /todos/{id}`

### Note
- `GET /notes`
- `GET /notes/{id}`
- `GET /notes/type`
- `GET /notes/todo/{todoId}`
- `POST /notes`
- `PUT /notes/{id}`
- `DELETE /notes/{id}`

(All secured except login & register)

---

## 🧪 Design

- Controller → Service → Repository structure
- DTO used for request & response
- Global security configuration
- Custom access denied & authentication entry point
- Stateless API (no session)
- Global Exception Handling
- Global Response Method

---

## 📌 Purpose

This project is built to:
- Practice **Spring Boot backend development**
- Learn **JWT security properly**
- Understand **REST API design**
- Prepare for backend developer roles

Still learning, still improving 💪

---

## 🛠 Future Improvements (Maybe)

- Swagger / OpenAPI
- Unit & integration tests
- Refresh token rotation
- Docker support
- Redis for token revocation

---
