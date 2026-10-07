package com.krev.auth.infrastructure.controller;

import com.krev.auth.application.port.in.UserGetInformationsUseCase;
import com.krev.auth.infrastructure.controller.dto.UserInformationsResponse;
import com.krev.auth.infrastructure.controller.mapper.UserMapper;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserGetInformationsUseCase userGetInformations;

    private final UserMapper mapper;

    public UserController(UserGetInformationsUseCase userGetInformations) {
        this.userGetInformations = userGetInformations;
        this.mapper = new UserMapper();
    }

    @GetMapping("/me")
    public UserInformationsResponse GetUserInformations(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();

        return mapper.toController(userGetInformations.execute(userId));
    }
}
