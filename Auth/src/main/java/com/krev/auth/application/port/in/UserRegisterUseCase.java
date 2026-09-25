package com.krev.auth.application.port.in;

import com.krev.auth.application.port.in.dto.RegisterDto;
import com.krev.auth.application.port.in.dto.RegisterReturnDto;

public interface UserRegisterUseCase {
    RegisterReturnDto execute(RegisterDto body);
}
