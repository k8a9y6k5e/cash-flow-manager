package com.krev.finances.application.usecase;

import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import com.krev.finances.domain.valueobject.MovementType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GetMovementPerMonthTestService {
    @Mock
    private MovementRepositoryPort repository;

    @InjectMocks
    private GetMovementPerMonthService service;

    @Test
    public void shouldReturnListedMovementsByMonth() {
        GetMovementPerMonthDto body = new GetMovementPerMonthDto(1, 2026);

        List<Movement> movements = new ArrayList<>();

        Movement movement = new Movement(
                MovementType.EXPENSE,
                10L,
                LocalDate.of(2026, 01, 17)
        );

        movements.add(movement);

        when(repository.findByDateBetween(
                    any(LocalDate.class),
                    any(LocalDate.class))
            )
            .thenReturn(movements);

        List<Movement> result = service.execute(body);

        ArgumentCaptor<LocalDate> initialDate = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> finalDate = ArgumentCaptor.forClass(LocalDate.class);

        verify(repository).findByDateBetween(
                initialDate.capture(),
                finalDate.capture()
        );

        assertEquals(movements, result);
    }
}
