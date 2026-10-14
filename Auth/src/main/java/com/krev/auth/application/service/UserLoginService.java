package com.krev.auth.application.service;

import com.krev.auth.application.port.in.UserLoginUseCase;
import com.krev.auth.application.port.in.dto.LoginDto;
import com.krev.auth.application.port.in.dto.LoginReturnDto;
import com.krev.auth.application.port.out.TokenGeneratorUseCase;
import com.krev.auth.application.port.out.UserRepositoryAdapter;
import com.krev.auth.domain.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserLoginService implements UserLoginUseCase {
    private final UserRepositoryAdapter userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenGeneratorUseCase tokenGenerator;

    public UserLoginService(
            UserRepositoryAdapter userRepository,
            TokenGeneratorUseCase tokenGenerator
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public LoginReturnDto execute(LoginDto body) {
        User user = findUser(body.username(), body.email());

        if(!passwordEncoder.matches(body.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        return new LoginReturnDto(
                tokenGenerator.generateToken(user.getId())
        );
    }

    private User findUser(String username, String email) {
        try {
            if(email != null && !email.isBlank()){
                return userRepository.findByEmail(email);
            }
            else if (username != null && !username.isBlank()){
                return userRepository.findByUsername(username);
            }
            else {
                throw new Exception("No one valid value passed");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
