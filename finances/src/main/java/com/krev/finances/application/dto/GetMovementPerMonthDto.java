package com.krev.finances.application.dto;

import jakarta.validation.constraints.NotNull;

public record GetMovementPerMonthDto(
        @NotNull
        Integer month,

        Integer year
) {
}
