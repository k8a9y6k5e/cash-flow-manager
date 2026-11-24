package com.krev.auth.infrastructure.adapter.out.persistence.processedMovement;

import com.krev.auth.application.port.out.ProcessedMovementRepository;
import org.springframework.stereotype.Component;

@Component
public class ProcessedMovementPersistenceAdapter implements ProcessedMovementRepository {
    private final ProcessedMovementJpaRepository repository;

    public ProcessedMovementPersistenceAdapter(ProcessedMovementJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean exists(String movementId) {
        return repository.existsById(movementId);
    }

    @Override
    public void markProcessed(String movementId) {
        repository.save(new ProcessedMovementJpaEntity(movementId));
    }
}
