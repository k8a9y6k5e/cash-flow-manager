package com.krev.finances.application.port.out;

import com.krev.finances.application.event.MovementConfirmatedRequestedEvent;

public interface MovementConfirmationPublisher {
    void publish(MovementConfirmatedRequestedEvent request, String movementId);
}
