package com.krev.auth.infrastructure.configuration;

import com.krev.auth.infrastructure.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter filter;

    public SecurityConfiguration(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                auth -> auth.requestMatchers(
                            "/auth/register",
                            "/csrf"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        );

        http.addFilterBefore(
                filter,
                UsernamePasswordAuthenticationFilter.class
        );

        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
