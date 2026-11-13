package com.krev.finances.application.event;

public record MovementConfirmatedRequestedEvent(
        String movementType,
        Long amount,
        Long userId
) {
}
