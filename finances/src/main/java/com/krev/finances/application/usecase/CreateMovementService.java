package com.krev.finances.application.usecase;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.event.MovementConfirmatedRequestedEvent;
import com.krev.finances.application.port.in.CreateMovementUseCase;
import com.krev.finances.application.port.out.MovementConfirmationPublisher;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.application.port.out.MovementRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class CreateMovementService implements CreateMovementUseCase {
    private final MovementRepositoryPort repositoryPort;

    private final MovementConfirmationPublisher movementConfirmationPublisher;

    public CreateMovementService(
            MovementRepositoryPort repositoryPort,
            MovementConfirmationPublisher movementConfirmationPublisher
    ) {
        this.repositoryPort = repositoryPort;
        this.movementConfirmationPublisher = movementConfirmationPublisher;
    }

    @Override
    public Movement execute(CreateMovementDto body) {
        Movement movement = new Movement(body.type(), body.amount(), body.description(), body.userId());

        Movement savedMovement = repositoryPort.save(movement);

        movementConfirmationPublisher.publish(toPublishRequest(savedMovement), savedMovement.getId().toString());

        return savedMovement;
    }

    private MovementConfirmatedRequestedEvent toPublishRequest(Movement movement) {
        return new MovementConfirmatedRequestedEvent(
                movement.getType().toString(),
                movement.getAmount(),
                movement.getUserId()
        );
    }
}
