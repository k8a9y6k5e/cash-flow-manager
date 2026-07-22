package com.krev.finances.infrastructure.persistence;

import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class MovementRepositoryAdapter implements MovementRepositoryPort {
    private final MovementJpaRepository repository;

    public MovementRepositoryAdapter(MovementJpaRepository repository) {
        this.repository = repository;
    }

    private MovementEntity toEntity(Movement domain) {
        MovementEntity entity = new MovementEntity();

        entity.setAmount(domain.getAmount());

        entity.setType(domain.getType());

        return entity;
    }

    private Movement toDomain(MovementEntity entity) {
        return new Movement(
                entity.getId(),
                entity.getType(),
                entity.getAmount()
        );
    }

    @Override
    public Movement save(Movement movement) {
        return toDomain(repository.save(toEntity(movement)));
    }
}
