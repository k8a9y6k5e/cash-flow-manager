package com.krev.auth.infrastructure.adapter.out.persistence.processedMovement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedMovementJpaRepository extends JpaRepository<ProcessedMovementJpaEntity, String> {
}
