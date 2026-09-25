package com.krev.auth.infrastructure.controller;

import com.krev.auth.application.port.in.UserRegisterUseCase;
import com.krev.auth.application.port.in.dto.RegisterDto;
import com.krev.auth.infrastructure.controller.dto.RegisterUserRequest;
import com.krev.auth.infrastructure.controller.dto.RegisterUserResponse;
import com.krev.auth.infrastructure.controller.mapper.AuthMapper;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRegisterUseCase userRegisterUseCase;
    private final AuthMapper authMapper;

    public AuthController(UserRegisterUseCase userRegisterUseCase) {
        this.userRegisterUseCase = userRegisterUseCase;
        this.authMapper = new AuthMapper();
    }

    @PostMapping("/register")
    public RegisterUserResponse Register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterDto body = authMapper.toApplication(registerUserRequest);

        return authMapper.toController(userRegisterUseCase.execute(body));
    }
}
