package com.krev.auth.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                auth -> auth.requestMatchers(
                            "/auth/register",
                            "/auth/login"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        );

        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(
                        Customizer.withDefaults()
                )
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
