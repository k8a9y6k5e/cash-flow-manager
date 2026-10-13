package com.krev.auth.application.service;

import com.krev.auth.application.port.in.UserGetInformationsUseCase;
import com.krev.auth.application.port.in.dto.UserInformationsDto;
import com.krev.auth.application.port.out.UserRepositoryAdapter;
import com.krev.auth.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserGetInformationsService implements UserGetInformationsUseCase {
    private final UserRepositoryAdapter userRepository;

    public UserGetInformationsService(UserRepositoryAdapter userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserInformationsDto execute(Long id) {
        User user = userRepository.findById(id);

        return this.toResponse(user);
    }

    private UserInformationsDto toResponse(User user){
        return new UserInformationsDto(
          user.getUsername(),
          user.getEmail()
        );
    }
}
