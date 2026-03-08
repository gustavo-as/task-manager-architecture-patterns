# MVC → Clean Architecture: Guia de Migração (Task)

## Estrutura Antes e Depois

```
❌ MVC (antes)                               ✅ CLEAN ARCHITECTURE (depois)
─────────────────────────────────────────────────────────────────
model/
  Task.java        (@Entity)                    domain/entity/
                                                - Task.java          (pura, sem @Entity)
                                                - TaskId.java        (Value Object)
                                                domain/enums/
                                                - TaskStatus.java
                                                domain/repository/
                                                - TaskRepository.java  (INTERFACE)

repository/
  TaskRepository   (extends                     infra/persistence/springdata/
  JpaRepository)     ← acoplado                 - TaskJpaAdapter.java     (implements TaskRepository)
                                                - TaskJpaEntity.java     (@Entity isolada aqui)
                                                - TaskJpaMapper.java     (converte domínio ↔ JPA)
                                                - SpringDataTaskRepository.java (JpaRepository escondido)

                                                infra/persistence/jdbc/
                                                - TaskJdbcAdapter.java   (implements TaskRepository, sem ORM)

service/
  TaskService.java                              application/usecase/
  (regras + infra                               - CreateTaskUseCase.java
   misturadas)                                  - CompleteTaskUseCase.java

controller/
  TaskController.java                           infra/web/
  (devolve @Entity)                             - TaskController.java    (devolve DTO, não @Entity)

                                                config/
                                                - TaskConfig.java        (wiring dos use cases)
```

---

## Os 5 Problemas do MVC e como foram resolvidos

### 1. @Entity no domínio → acoplamento com JPA

```java
// ❌ MVC: domínio conhece o banco
@Entity
@Table(name = "tasks")
public class Task {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;       // ID gerado pelo banco → não existe antes de salvar
    // sem comportamento, só getters/setters
}

// ✅ Clean Arch: domínio puro, ID gerado pelo próprio domínio
public class Task {
    private final TaskId id;   // UUID gerado em Task.create() antes de qualquer save
    // comportamento de negócio aqui
}
```

---

### 2. Regras de negócio no Service → modelo anêmico

```java
// ❌ MVC Service: "task já completa" vive no service
public Task completeTask(Long id) {
    Task task = repo.findById(id)...;
    if (task.getStatus() == TaskStatus.DONE) {     // ← regra de negócio aqui
        throw new IllegalStateException("...");
    }
    task.setStatus(TaskStatus.DONE);               // ← mutação via setter
    return repo.save(task);
}

// ✅ Clean Arch: "task já completa" vive na entidade
// CompleteTaskUseCase:
task.complete();   // ← delega para a entidade

// Task.java (domínio):
public void complete() {
    if (this.status == TaskStatus.DONE) {          // ← regra no lugar certo
        throw new IllegalStateException("Task is already completed.");
    }
    this.status = TaskStatus.DONE;
    this.updatedAt = LocalDateTime.now();
}
```

---

### 3. Infraestrutura misturada no Service → acoplamento e dificuldade de teste

No MVC é comum o `TaskService` acumular responsabilidades que não são negócio:

```java
// ❌ MVC: service com @Transactional, cache, e-mail, S3 e @Async juntos
@Service
@Transactional                              // ← detalhe de persistência JPA
public class TaskService {

    private final TaskRepository  taskRepository;
    private final JavaMailSender  mailSender;   // ← infra de e-mail
    private final S3Client        s3Client;     // ← infra de cloud storage

    @Cacheable(value = "tasks", key = "#id")    // ← detalhe de cache (Redis/EhCache)
    public Task findById(Long id) { ... }

    @CacheEvict(value = "tasks", key = "#id")
    public Task completeTask(Long id) {
        // regra de negócio + invalidação de cache + envio de e-mail no mesmo método
        task.setStatus(DONE);
        mailSender.send(...);               // ← SMTP dentro do service de negócio
        s3Client.putObject(...);            // ← AWS SDK dentro do service de negócio
    }

    @Async                                      // ← thread pool gerenciada pelo Spring
    private void sendCompletionEmail(...) { ... }
}

// Consequência: para testar "não pode completar task já concluída" é preciso mockar:
@Mock TaskRepository taskRepository;
@Mock JavaMailSender mailSender;        // ← não tem nada a ver com a regra sendo testada
@Mock S3Client s3Client;               // ← idem
```

Na Clean Architecture cada responsabilidade vai para o lugar certo:

```
Responsabilidade        MVC (onde estava)    Clean Arch (onde vai)
──────────────────────  ───────────────────  ──────────────────────────────────
@Transactional          TaskService          TaskJpaAdapter            (infra)
@Cacheable/@CacheEvict  TaskService          CachedTaskRepository      (infra decorator)
JavaMailSender          TaskService          EmailNotificationAdapter   (infra)
@Async                  TaskService          EmailNotificationAdapter   (infra)
S3Client / AWS SDK      TaskService          S3FileStorageAdapter       (infra)
Regras de negócio       TaskService          Task                       (domínio)
Orquestração            TaskService          Use Cases                  (application)
```

O padrão é sempre o mesmo: define-se uma **interface no domínio/application** e a implementação concreta com o framework fica num adapter na infra:

```java
// ✅ Clean Arch: interface no domínio — sem JavaMailSender, sem AWS SDK
public interface TaskNotificationPort {
    void notifyTaskCreated(TaskId taskId, String ownerEmail);
    void notifyTaskCompleted(TaskId taskId, String ownerEmail);
}

public interface FileStoragePort {
    String upload(String folder, String filename, byte[] content);
}

// ✅ Use Case depende das interfaces, nunca dos SDKs diretamente
public class CompleteTaskUseCase {
    private final TaskRepository       taskRepository;
    private final TaskNotificationPort notificationPort; // ← não sabe que é e-mail

    public Output execute(Input input) {
        Task task = taskRepository.findById(TaskId.of(input.taskId()))
            .orElseThrow(() -> new TaskNotFoundException(...));

        task.complete();                                 // ← regra na entidade
        taskRepository.save(task);
        notificationPort.notifyTaskCompleted(...);       // ← sem saber o "como"
        ...
    }
}

// ✅ Trocar e-mail por push notification?
//    Cria PushNotificationAdapter implements TaskNotificationPort
//    Zero mudança no use case, zero mudança no domínio.

// ✅ Teste da regra de negócio: zero mocks de infra
Task task = Task.create("Título", "Desc");
task.complete();
assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
```

---


### 4. TaskRepository extends JpaRepository → contrato de infra exposto

```java
// ❌ MVC: o contrato já é JPA desde a definição
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {}
//                                        ↑ JPA    ↑ @Entity  ↑ Long do banco
// Quem usa TaskRepository está acoplado ao JPA.

// ✅ Clean Arch: contrato limpo no domínio
public interface TaskRepository {           // pacote: domain.repository
    Task save(Task task);
    Optional<Task> findById(TaskId id);
    // ...
}
// Quem usa só vê Task (domínio) e TaskId (value object)
// A implementação (JPA ou JDBC) fica escondida na infra
```

---

### 5. Controller devolvendo @Entity JPA

```java
// ❌ MVC: @Entity vazando para a resposta HTTP
public ResponseEntity<Task> createTask(...) {
    Task task = taskService.createTask(...);
    return ResponseEntity.ok(task);   // serializa @Entity com anotações JPA
}

// ✅ Clean Arch: DTO limpo
public ResponseEntity<TaskResponse> createTask(...) {
    var output = createTaskUseCase.execute(...);
    return ResponseEntity.ok(new TaskResponse(output.id(), output.title(), output.status()));
}
```

---

### 6. Dependência de teste do JPA

```java
// ❌ MVC: para testar regra de negócio, precisa mockar JPA
@Mock TaskRepository taskRepository;
when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
// levantar contexto Spring para @SpringBootTest, etc.

// ✅ Clean Arch: teste de domínio puro
Task task = Task.create("Título", "Desc");  // sem mock, sem Spring
task.complete();
assertThat(task.getStatus()).isEqualTo(TaskStatus.DONE);
// milissegundos, zero infraestrutura
```

---

## Independência de ORM: trocar JPA por JDBC

```
Use Case → TaskRepository (interface do domínio)
                 ↑
     ┌───────────┴───────────┐
     TaskJpaAdapter      TaskJdbcAdapter
     (Spring Data JPA)   (JdbcTemplate, SQL direto)
     Hibernate            Zero ORM

Para trocar: adicione @Primary no TaskJdbcAdapter
Impacto em Task.java:            ZERO
Impacto em CreateTaskUseCase:    ZERO
Impacto em TaskController:       ZERO
```

---

## Fluxo completo de uma requisição

```
POST /api/tasks  { "title": "...", "description": "..." }
       ↓
TaskController          — converte Request → Input
       ↓
CreateTaskUseCase       — orquestra (sem regras de negócio)
       ↓
Task.create(title, desc)— valida e cria a entidade (domínio)
       ↓
TaskRepository.save()   — interface do domínio
       ↓
TaskJpaAdapter          — converte Task → TaskJpaEntity
       ↓
SpringDataTaskRepository— persiste com Hibernate
       ↓
TaskJpaMapper.toDomain()— converte TaskJpaEntity → Task
       ↓
CreateTaskUseCase       — monta Output
       ↓
TaskController          — converte Output → TaskResponse (HTTP 201)
```