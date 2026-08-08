package com.krev.finances.infrastructure.exceptions;

public class InvalidMovementTypeException extends  InfrastructureException{
    public InvalidMovementTypeException(String message) {
        super(message);
    }
}
