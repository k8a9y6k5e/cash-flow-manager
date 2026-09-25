package com.krev.auth.infrastructure.controller.mapper;

import com.krev.auth.application.port.in.dto.RegisterDto;
import com.krev.auth.application.port.in.dto.RegisterReturnDto;
import com.krev.auth.domain.entity.User;
import com.krev.auth.infrastructure.controller.dto.RegisterUserRequest;
import com.krev.auth.infrastructure.controller.dto.RegisterUserResponse;

public class AuthMapper {
    public RegisterDto toApplication(RegisterUserRequest request) {
        return new RegisterDto(
                request.username(),
                request.email(),
                request.password()
        );
    }

    public RegisterUserResponse toController(RegisterReturnDto body) {
        return new RegisterUserResponse(
                body.token()
        );
    }
}
