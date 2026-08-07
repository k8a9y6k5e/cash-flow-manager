package com.krev.finances.infrastructure.persistence;

import com.krev.finances.domain.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovementJpaRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByDateBetween(LocalDate initialDate, LocalDate finalDate);
}
