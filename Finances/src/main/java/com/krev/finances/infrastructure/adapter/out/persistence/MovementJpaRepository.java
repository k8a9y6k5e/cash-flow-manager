package com.krev.finances.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByDateBetweenAndUserId(LocalDate initialDate, LocalDate finalDate, Long userId);
}
