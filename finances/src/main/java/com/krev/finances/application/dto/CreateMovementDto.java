package com.krev.finances.application.dto;

import com.krev.finances.domain.valueobject.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMovementDto(
        @NotBlank(message = "Type it's not valid value")
        MovementType type,

        @NotNull(message = "Amount need a value")
        @Min(value = 1, message = "Amount need movement a valid quantity")
        Long amount
) {
}
