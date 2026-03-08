# MVC → Clean Architecture: Guia de Migração com Task

## Estrutura de Pastas

```
# ❌ MVC (antes)
src/
├── model/        Task.java           ← @Entity no domínio
├── repository/   TaskRepository.java ← extends JpaRepository
├── service/      TaskService.java    ← regras + infra misturadas
└── controller/   TaskController.java