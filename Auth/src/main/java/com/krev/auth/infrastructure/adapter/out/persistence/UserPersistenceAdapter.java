package com.krev.auth.infrastructure.adapter.out.persistence;

import com.krev.auth.application.port.out.UserRepositoryAdapter;
import com.krev.auth.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements UserRepositoryAdapter {
    private final UserJpaRepository userJpaRepository;

    public UserPersistenceAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = this.toEntity(user);

        return this.toDomain(userJpaRepository.save(entity));
    }

    @Override
    public User findById(Long id) {
        return this.toDomain(userJpaRepository.findById(id).orElseThrow());
    }

    private UserJpaEntity toEntity(User user){
        UserJpaEntity entity = new UserJpaEntity();

        if(!(user.getId() == null)) {
            entity.setId(user.getId());
        }

        entity.setUsername(user.getUsername());
        entity.setPassword(user.getPassword());
        entity.setEmail(user.getEmail());

        return entity;
    }

    private User toDomain(UserJpaEntity entity){
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getEmail()
        );
    }
}
