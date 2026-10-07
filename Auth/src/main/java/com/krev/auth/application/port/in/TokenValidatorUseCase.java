package com.krev.auth.application.port.in;

public interface TokenValidatorUseCase {
    Long execute(String token) throws Exception;
}
