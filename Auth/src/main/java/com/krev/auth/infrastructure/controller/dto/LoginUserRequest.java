package com.krev.auth.infrastructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record LoginUserRequest(
        String username,

        @Size(min = 8)
        String password,

        @Email
        String email
) {
}
