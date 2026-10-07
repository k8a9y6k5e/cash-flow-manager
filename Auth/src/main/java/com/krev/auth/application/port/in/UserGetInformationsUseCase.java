package com.krev.auth.application.port.in;

import com.krev.auth.application.port.in.dto.UserInformationsDto;

public interface UserGetInformationsUseCase {
    UserInformationsDto execute(Long id);
}
