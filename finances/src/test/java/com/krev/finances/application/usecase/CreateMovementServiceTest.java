package com.krev.finances.application.usecase;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.valueobject.MovementType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.krev.finances.domain.ports.MovementRepositoryPort;

@ExtendWith(MockitoExtension.class)
public class CreateMovementServiceTest {
    @Mock
    private MovementRepositoryPort repository;

    @InjectMocks
    private CreateMovementService service;

    @Test
    public void shouldCreateMovement() {
        CreateMovementDto body = new CreateMovementDto(MovementType.INCOME, 10L);

        Movement movement = new Movement(body.type(), body.amount());

        when(repository.save(any(Movement.class))).thenReturn(movement);

        Movement result = service.execute(body);

        ArgumentCaptor<Movement> captor = ArgumentCaptor.forClass(Movement.class);

        verify(repository).save(captor.capture());

        assertEquals(MovementType.INCOME, result.getType());

        assertEquals(10L, result.getAmount());
    }
}
