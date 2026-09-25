package com.krev.auth.infrastructure.controller.dto;

import jakarta.validation.constraints.*;

public record RegisterUserRequest(
        @NotBlank
        @NotNull
        String username,

        @Size(min = 8)
        String password,

        @NotBlank
        @NotNull
        @Email
        String email
) {
}
