package com.krev.auth.application.port.in;

import com.krev.auth.application.port.in.dto.LoginDto;
import com.krev.auth.application.port.in.dto.LoginReturnDto;

public interface UserLoginUseCase {
    public LoginReturnDto execute(LoginDto body);
}
