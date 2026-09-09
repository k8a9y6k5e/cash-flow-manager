package com.krev.finances.infrastructure.persistence;

import com.krev.finances.domain.entity.Movement;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovementTestRepositoryAdapter {
    @Mock
    private MovementJpaRepository repository;

    @InjectMocks
    private MovementRepositoryAdapter adapter;

    @Test
    public void saveMovementTest() {
        Movement movement = new Movement(1L, MovementType.INCOME, 10L);

        MovementEntity entity = new MovementEntity();

        entity.setId(movement.getId());
        entity.setAmount(movement.getAmount());
        entity.setDate(movement.getDate());
        entity.setType(movement.getType());

        when(repository.save(any(MovementEntity.class))).thenReturn(entity);

        Movement result = adapter.save(movement);

        assertEquals(movement.getAmount(), result.getAmount());
        assertEquals(movement.getDate(), result.getDate());
        assertEquals(movement.getType(), result.getType());
    }

    @Test
    public void findByDateBetweenTest() {
        Movement movement = new Movement(1L, MovementType.INCOME, 10L);

        MovementEntity entity = new MovementEntity();

        entity.setId(movement.getId());
        entity.setAmount(movement.getAmount());
        entity.setDate(movement.getDate());
        entity.setType(movement.getType());

        List<MovementEntity> movements = new ArrayList<>();

        movements.add(entity);

        when(repository.findByDateBetween(
                any(LocalDate.class),
                any(LocalDate.class)
        )).thenReturn(movements);

        List<Movement> result = adapter.findByDateBetween(
                LocalDate.now().minusMonths(1),
                LocalDate.now()
        );

        assertEquals(movements.get(0).getAmount(), result.get(0).getAmount());
        assertEquals(movements.get(0).getDate(), result.get(0).getDate());
        assertEquals(movements.get(0).getType(), result.get(0).getType());
    }
}
