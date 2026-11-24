package com.krev.auth.application.port.out;

public interface ProcessedMovementRepository {
    boolean exists(String movementId);

    void markProcessed(String movementId);
}
