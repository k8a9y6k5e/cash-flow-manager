package com.krev.auth.infrastructure.adapter.out.persistence.processedMovement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "processed_movement")
@Getter
@NoArgsConstructor
public class ProcessedMovementJpaEntity {
    @Id
    @Column(name = "movement_id", nullable = false)
    private String movementId;

    @Column(name = "processed_at", nullable = false)
    private LocalDateTime processedAt;

    public ProcessedMovementJpaEntity(String movementId) {
        this.movementId = movementId;
        this.processedAt = LocalDateTime.now();
    }
}
