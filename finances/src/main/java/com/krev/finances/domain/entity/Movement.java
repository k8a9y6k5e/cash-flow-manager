package com.krev.finances.domain.entity;

import com.krev.finances.domain.exceptions.DomainEntityException;
import com.krev.finances.domain.valueobject.MovementType;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Movement {
    private Long id;

    private MovementType type;

    private Long amount;

    private LocalDate date;

    public Movement(Long id, MovementType type, Long amount) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        else {
            throw new DomainEntityException("Invalid amount value entered");
        }

        this.id = id;
        this.type = type;
        this.date = LocalDate.now();
    }

    public Movement(MovementType type, Long amount) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        else {
            throw new DomainEntityException("Invalid amount value entered");
        }

        this.type = type;
        this.date = LocalDate.now();
    }

    public Movement(Long id, MovementType type, Long amount, LocalDate date) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        else {
            throw new DomainEntityException("Invalid amount value entered");
        }

        this.id = id;
        this.type = type;
        this.date = date;
    }

    public Movement(MovementType type, Long amount, LocalDate date) {
        if(amount != null && amount > 0L) {
            this.amount = amount;
        }
        else {
            throw new DomainEntityException("Invalid amount value entered");
        }

        this.type = type;
        this.date = date;
    }
}
