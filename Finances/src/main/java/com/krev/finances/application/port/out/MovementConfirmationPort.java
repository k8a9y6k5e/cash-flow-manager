package com.krev.finances.application.port.out;

import com.krev.finances.application.event.MovementConfirmatedRequestedEvent;

public interface MovementConfirmationPort {
    void publish(MovementConfirmatedRequestedEvent request);
}
