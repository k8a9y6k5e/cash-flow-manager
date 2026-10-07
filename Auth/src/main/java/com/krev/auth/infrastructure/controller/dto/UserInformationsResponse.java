package com.krev.auth.infrastructure.controller.dto;

public record UserInformationsResponse(
        String username,
        String email
) {
}
