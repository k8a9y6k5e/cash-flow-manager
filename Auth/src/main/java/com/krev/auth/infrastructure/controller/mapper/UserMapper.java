package com.krev.auth.infrastructure.controller.mapper;

import com.krev.auth.application.port.in.dto.UserInformationsDto;
import com.krev.auth.infrastructure.controller.dto.UserInformationsResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserInformationsResponse toController(UserInformationsDto dto) {
        return new UserInformationsResponse(
                dto.username(),
                dto.email()
        );
    }
}
