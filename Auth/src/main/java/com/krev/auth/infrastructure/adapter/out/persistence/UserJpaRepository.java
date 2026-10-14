package com.krev.auth.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    public Optional<UserJpaEntity> findByEmail(String email);
    public Optional<UserJpaEntity> findByUsername(String username);
}
