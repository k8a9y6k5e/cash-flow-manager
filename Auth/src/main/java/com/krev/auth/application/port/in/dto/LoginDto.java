package com.krev.auth.application.port.in.dto;

public record LoginDto(
        String email,
        String username,
        String password
) {
}
