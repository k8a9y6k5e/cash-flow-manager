package com.krev.finances.e2e.dto;

public record LoginAuthRequest(
        String email,
        String username,
        String password
) {
}
