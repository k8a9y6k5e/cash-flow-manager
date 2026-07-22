package com.krev.finances.application.usecase;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.port.in.CreateMovementUseCase;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateMovementService implements CreateMovementUseCase {
    private final MovementRepositoryPort repositoryPort;

    public CreateMovementService(MovementRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Movement execute(CreateMovementDto body) {
        Movement movement = new Movement(body.type(), body.amount());

        return repositoryPort.save(movement);
    }
}
