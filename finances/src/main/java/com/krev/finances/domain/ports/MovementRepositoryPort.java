package com.krev.finances.domain.ports;

import com.krev.finances.domain.entity.Movement;

import java.time.LocalDate;
import java.util.List;

public interface MovementRepositoryPort {
    Movement save(Movement movement);
    List<Movement> findByDateBetween(LocalDate initialDate, LocalDate finalDate);
}
