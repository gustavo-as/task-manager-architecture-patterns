#  Task Manager — Architecture Patterns

> Comparing **MVC** and **Clean Architecture** through a simple task manager built in Java.  
> Explore how each pattern organizes code, handles dependencies, and separates responsibilities.

---

##  Objectives

- Implement the same task manager using two different architectural patterns
- Understand the structure, layers, and responsibilities of each approach
- Highlight the differences in testability, maintainability, and scalability
- Serve as a practical reference for developers learning software architecture

---

##  Project Structure

```
task-manager-architecture-patterns/
│
├── mvc/
│   ├── src/
│   │   ├── model/          # Data and business rules
│   │   ├── view/           # User interface / output
│   │   └── controller/     # Input handling and flow control
│   ├── test/
│   └── README.md
│
├── clean-architecture/
│   ├── src/
│   │   ├── domain/         # Entities and business rules
│   │   ├── application/    # Use cases
│   │   ├── infrastructure/ # DB, frameworks, external tools
│   │   └── presentation/   # Controllers, CLI, API
│   ├── test/
│   └── README.md
│
└── README.md
```

---

##  MVC vs Clean Architecture

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

---

##  How to Run

### Prerequisites

- Java 17+
- Maven 3.8+

### MVC

```bash
cd mvc
mvn compile
mvn exec:java -Dexec.mainClass="com.taskmanager.mvc.Main"
```

### Clean Architecture

```bash
cd clean-architecture
mvn compile
mvn exec:java -Dexec.mainClass="com.taskmanager.clean.Main"
```

### Running Tests

```bash
# MVC
cd mvc && mvn test

# Clean Architecture
cd clean-architecture && mvn test
```

---

##  Features

Both implementations cover the same set of features:

- [x] Create a task
- [x] List all tasks
- [x] Mark a task as completed
- [x] Delete a task

---
