package com.krev.finances.application.mapper;

import com.krev.finances.application.dto.CreateMovementResponseDto;
import com.krev.finances.domain.entity.Movement;

public final class MovementMapper {
    private MovementMapper() {}

    public static CreateMovementResponseDto toCreateResponse(Movement movement){
        return new CreateMovementResponseDto(
                movement.getId(),
                movement.getType(),
                movement.getAmount()
                );
    }
}
