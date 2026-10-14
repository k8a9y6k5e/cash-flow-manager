package com.krev.auth.infrastructure.security;

import com.krev.auth.application.port.out.TokenGeneratorUseCase;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.*;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenGenerator implements TokenGeneratorUseCase {
    private final PrivateKey privateKey;

    public JwtTokenGenerator(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    @Override
    public String generateToken(Long userId) {
        Instant expirationTime = Instant.now().plus(5, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(userId.toString())
                .signWith(privateKey)
                .expiration(Date.from(expirationTime))
                .compact();
    }
}
