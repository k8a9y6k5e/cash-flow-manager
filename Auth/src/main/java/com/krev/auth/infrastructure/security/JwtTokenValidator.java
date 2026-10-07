package com.krev.auth.infrastructure.security;

import com.krev.auth.application.port.in.TokenValidatorUseCase;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

@Component
public class JwtTokenValidator implements TokenValidatorUseCase {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Long execute(String completeToken) throws Exception {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        try{
            if(
                    !completeToken.split(" ")[0].equals("Bearer") ||
                            completeToken.split(" ")[1].isBlank()
            ){
                throw new Exception("Invalid token");
            }

            String token = completeToken.split(" ")[1];

            return Long.parseLong(Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getBody()
                    .getSubject());
        }
        catch (Exception ex){
            throw new Exception(ex.getMessage());
        }
    }
}
