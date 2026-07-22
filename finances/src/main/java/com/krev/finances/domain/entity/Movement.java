package com.krev.finances.domain.entity;

import com.krev.finances.domain.valueobject.MovementType;
import lombok.Getter;

@Getter
public class Movement {
    private Long id;

    private MovementType type;

    private Long amount;

    public Movement(Long id, MovementType type, Long amount) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        //and an error throw

        this.id = id;
        this.type = type;
    }

    public Movement(MovementType type, Long amount) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        //and an error throw

        this.type = type;
    }
}
