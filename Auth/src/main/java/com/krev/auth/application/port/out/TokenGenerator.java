package com.krev.auth.application.port.out;

public interface TokenGenerator {
    String generateToken(Long userId);
}
