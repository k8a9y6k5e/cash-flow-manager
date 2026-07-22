package com.krev.finances.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, Long> {
}
