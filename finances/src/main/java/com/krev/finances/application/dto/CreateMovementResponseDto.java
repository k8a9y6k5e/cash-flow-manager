package com.krev.finances.application.dto;

import com.krev.finances.domain.valueobject.MovementType;

public record CreateMovementResponseDto(
        Long id,
        MovementType type,
        Long amount
) {
}
