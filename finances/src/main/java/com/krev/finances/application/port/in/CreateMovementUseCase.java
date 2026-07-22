package com.krev.finances.application.port.in;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.domain.entity.Movement;

public interface CreateMovementUseCase {
    Movement execute(CreateMovementDto body);
}
