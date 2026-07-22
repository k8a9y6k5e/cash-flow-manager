package com.krev.finances.infrastructure.persistence;

import com.krev.finances.domain.valueobject.MovementType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "movement")
@EntityListeners(AuditingEntityListener.class)
@Data
public class MovementEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type", nullable = false)
    private MovementType type;

    @Column(name = "amount", nullable = false)
    private Long amount;
}