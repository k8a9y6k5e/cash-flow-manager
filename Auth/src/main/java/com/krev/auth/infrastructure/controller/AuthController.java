package com.krev.auth.infrastructure.controller;

import com.krev.auth.application.port.in.UserLoginUseCase;
import com.krev.auth.application.port.in.UserRegisterUseCase;
import com.krev.auth.application.port.in.dto.LoginDto;
import com.krev.auth.application.port.in.dto.RegisterDto;
import com.krev.auth.infrastructure.controller.dto.LoginUserRequest;
import com.krev.auth.infrastructure.controller.dto.LoginUserResponse;
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

    private final UserLoginUseCase userLoginUseCase;

    public AuthController(
            UserRegisterUseCase userRegisterUseCase,
            UserLoginUseCase userLoginUseCase
    ) {
        this.userRegisterUseCase = userRegisterUseCase;
        this.authMapper = new AuthMapper();
        this.userLoginUseCase = userLoginUseCase;
    }

    @PostMapping("/register")
    public RegisterUserResponse register(@RequestBody @Valid RegisterUserRequest registerUserRequest) {
        RegisterDto body = authMapper.toApplication(registerUserRequest);

        return authMapper.toController(userRegisterUseCase.execute(body));
    }

    @PostMapping("/login")
    public LoginUserResponse login(@RequestBody @Valid LoginUserRequest loginUserRequest) {
        LoginDto body = authMapper.toApplication(loginUserRequest);

        return authMapper.toController(userLoginUseCase.execute(body));
    }
}
