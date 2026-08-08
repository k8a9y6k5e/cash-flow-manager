package com.krev.finances.application.exceptions;

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}
