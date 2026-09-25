package com.krev.auth.application.port.out;

import com.krev.auth.domain.entity.User;

public interface UserRepositoryAdapter {
    User save(User user);
}
