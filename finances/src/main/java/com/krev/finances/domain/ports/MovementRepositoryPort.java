package com.krev.finances.domain.ports;

import com.krev.finances.domain.entity.Movement;

public interface MovementRepositoryPort {
    Movement save(Movement movement);
}
