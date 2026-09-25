package com.krev.auth.infrastructure.security;

import com.krev.auth.application.port.out.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtTokenGenerator implements TokenGenerator {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(Long userId) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        Instant expirationTime = Instant.now().plus(5, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(userId.toString())
                .signWith(key)
                .expiration(Date.from(expirationTime))
                .compact();
    }
}
