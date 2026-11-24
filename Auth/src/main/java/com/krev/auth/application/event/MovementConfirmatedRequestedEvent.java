package com.krev.auth.application.event;

public record MovementConfirmatedRequestedEvent(
        String movementType,
        Long amount,
        Long userId
) {
}

