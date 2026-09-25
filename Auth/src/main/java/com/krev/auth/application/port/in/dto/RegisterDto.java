package com.krev.auth.application.port.in.dto;

public record RegisterDto(
        String username,
        String email,
        String password
) {
}
