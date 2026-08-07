package com.krev.finances.infrastructure.persistence;

import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        entity.setDate(domain.getDate());

        return entity;
    }

    private Movement toDomain(MovementEntity entity) {
        return new Movement(
                entity.getId(),
                entity.getType(),
                entity.getAmount()
        );
    }

    private Movement toDatedDomain(MovementEntity entity) {
        return new Movement(
                entity.getId(),
                entity.getType(),
                entity.getAmount(),
                entity.getDate()
        );
    }

    private List<Movement> toListedDomain(List<MovementEntity> listedEntity) {
        List<Movement> movementList = new ArrayList<>();

        for(MovementEntity movementEntity : listedEntity) {
            movementList.add(toDatedDomain(movementEntity));
        }

        return movementList;
    }

    @Override
    public Movement save(Movement movement) {
        return toDomain(repository.save(toEntity(movement)));
    }

    @Override
    public List<Movement> findByDateBetween(LocalDate initialDate, LocalDate finalDate) {
        return toListedDomain(repository.findByDateBetween(initialDate, finalDate));
    }
}
