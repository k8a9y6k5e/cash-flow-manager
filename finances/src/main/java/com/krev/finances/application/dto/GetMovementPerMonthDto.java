package com.krev.finances.application.dto;

import jakarta.validation.constraints.NotNull;

public record GetMovementPerMonthDto(
        @NotNull(message = "Month doesn't passed")
        Integer month,

        Integer year
) {
}
