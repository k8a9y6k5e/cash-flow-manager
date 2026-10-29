package com.krev.finances.e2e.dto;

public record RegisterAuthRequest(
        String email,
        String username,
        String password
) {
}
