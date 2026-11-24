package com.krev.auth.infrastructure.adapter.in.kafka;

import com.krev.auth.application.event.MovementConfirmatedRequestedEvent;
import com.krev.auth.application.port.in.UpdateUserBalanceUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Header;

@Component
public class MovementConfirmationConsumer {
    private final UpdateUserBalanceUseCase updateUserBalanceUseCase;

    public MovementConfirmationConsumer(UpdateUserBalanceUseCase updateUserBalanceUseCase) {
        this.updateUserBalanceUseCase = updateUserBalanceUseCase;
    }

    @KafkaListener(topics = "movement.confirmation")
    public void receive(
            MovementConfirmatedRequestedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String movementId
    ) {
        updateUserBalanceUseCase.execute(
                movementId,
                event.movementType(),
                event.amount(),
                event.userId()
        );
    }
}
