package com.gustavohub.clean.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataTaskRepository extends JpaRepository<TaskJpaEntity, Long> {
}
