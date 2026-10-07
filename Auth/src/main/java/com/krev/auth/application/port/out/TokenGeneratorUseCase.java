package com.krev.auth.application.port.out;

public interface TokenGeneratorUseCase {
    String generateToken(Long userId);
}
