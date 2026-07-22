package com.krev.finances.application.dto;

import com.krev.finances.domain.valueobject.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMovementDto(
        @NotBlank
        MovementType type,

        @NotNull
        @Min(1)
        Long amount
) {
}
