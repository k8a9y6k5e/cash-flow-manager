package com.krev.auth.application.service;

import com.krev.auth.application.port.in.UserRegisterUseCase;
import com.krev.auth.application.port.in.dto.RegisterDto;
import com.krev.auth.application.port.in.dto.RegisterReturnDto;
import com.krev.auth.application.port.out.TokenGenerator;
import com.krev.auth.application.port.out.UserRepositoryAdapter;
import com.krev.auth.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRegisterService implements UserRegisterUseCase {
    private final UserRepositoryAdapter userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenGenerator tokenGenerator;

    public UserRegisterService(
            UserRepositoryAdapter userRepository,
            TokenGenerator tokenGenerator
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.tokenGenerator = tokenGenerator;
    }

    public RegisterReturnDto execute(RegisterDto body) {
        String password = passwordEncoder.encode(body.password());

        User user = userRepository.save(createUserValue(body, password));

        String token = tokenGenerator.generateToken(user.getId());

        return new RegisterReturnDto(token);
    }

    private User createUserValue(RegisterDto body, String password) {
        return new User(
                body.username(),
                password,
                body.email()
        );
    }
}
