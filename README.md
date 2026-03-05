# 📋 Task Manager — Architecture Patterns

> Comparing **MVC** and **Clean Architecture** through a simple task manager built in Java.  
> Explore how each pattern organizes code, handles dependencies, and separates responsibilities.

---

## 🎯 Objectives

- Implement the same task manager using two different architectural patterns
- Understand the structure, layers, and responsibilities of each approach
- Highlight the differences in testability, maintainability, and scalability
- Serve as a practical reference for developers learning software architecture

---

## 🗂️ Project Structure

```
task-manager-architecture-patterns/
│
├── mvc/
│   └── src/main/java/com/taskmanager/mvc/
│       ├── MvcApplication.java
│       ├── model/
│       │   ├── Task.java
│       │   ├── TaskRepository.java
│       │   └── InMemoryTaskRepository.java
│       └── controller/
│           └── TaskController.java
│
├── clean-architecture/
│   └── src/main/java/com/taskmanager/clean/
│       ├── CleanApplication.java
│       ├── domain/
│       │   ├── entity/Task.java
│       │   └── repository/TaskRepository.java
│       ├── application/usecase/
│       │   ├── CreateTaskUseCase.java
│       │   ├── GetAllTasksUseCase.java
│       │   ├── GetTaskByIdUseCase.java
│       │   ├── UpdateTaskUseCase.java
│       │   ├── CompleteTaskUseCase.java
│       │   └── DeleteTaskUseCase.java
│       ├── infrastructure/
│       │   ├── repository/InMemoryTaskRepository.java
│       │   └── config/BeanConfiguration.java
│       └── presentation/
│           ├── controller/TaskController.java
│           └── dto/
│               ├── TaskRequest.java
│               └── TaskResponse.java
│
└── README.md
```

---

## ⚔️ MVC vs Clean Architecture

| Aspect | MVC | Clean Architecture |
|---|---|---|
| **Layers** | Model, View, Controller | Domain, Application, Infrastructure, Presentation |
| **Main goal** | Separate UI from logic | Isolate business rules from external dependencies |
| **Dependency flow** | Controller → Model → View | Always inward (toward the domain) |
| **Testability** | Moderate — controller often depends on framework | High — domain and use cases are framework-free |
| **Scalability** | Good for small/medium apps | Better suited for complex, long-lived systems |
| **Learning curve** | Low | Moderate to high |
| **Framework coupling** | Usually tightly coupled | Decoupled by design |
| **Use case visibility** | Implicit (spread across layers) | Explicit (each use case is a class) |
| **DTOs** | Entity exposed directly in API | Request/Response DTOs protect the domain |

---

## 🔁 Request Flow Comparison

**MVC**
```
HTTP Request → TaskController → InMemoryTaskRepository → HTTP Response
```

**Clean Architecture**
```
HTTP Request → TaskController (presentation)
                    → UseCase (application)
                        → TaskRepository interface (domain)
                            → InMemoryTaskRepository (infrastructure)
                                → HTTP Response (via DTO)
```

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot 3.2**
- **Maven 3.8+**
- **Lombok**
- **In-memory storage** (HashMap)

---

## 🚀 How to Run

### Prerequisites

- Java 17+
- Maven 3.8+

### MVC — port `8080`

```bash
cd mvc
mvn spring-boot:run
```

### Clean Architecture — port `8081`

```bash
cd clean-architecture
mvn spring-boot:run
```

---

## 📡 API Endpoints

Both projects expose the same REST API:

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/tasks` | List all tasks |
| `GET` | `/tasks/{id}` | Get task by ID |
| `POST` | `/tasks` | Create a new task |
| `PUT` | `/tasks/{id}` | Update a task |
| `PATCH` | `/tasks/{id}/complete` | Mark task as completed |
| `DELETE` | `/tasks/{id}` | Delete a task |

### Request body (POST / PUT)

```json
{
  "title": "Study Clean Architecture",
  "description": "Read the book and implement examples"
}
```

### Response body

```json
{
  "id": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "title": "Study Clean Architecture",
  "description": "Read the book and implement examples",
  "completed": false,
  "createdAt": "2026-03-05T21:00:00"
}
```

---

## ✅ Features

- [x] Create a task
- [x] List all tasks
- [x] Get task by ID
- [x] Update a task
- [x] Mark a task as completed
- [x] Delete a task

---
