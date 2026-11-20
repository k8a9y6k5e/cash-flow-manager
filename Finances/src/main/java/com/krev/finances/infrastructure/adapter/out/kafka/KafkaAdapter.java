package com.krev.finances.infrastructure.adapter.out.kafka;

import com.krev.finances.application.event.MovementConfirmatedRequestedEvent;
import com.krev.finances.application.port.out.MovementConfirmationPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAdapter implements MovementConfirmationPublisher {
    private static final String MOVEMENT_CONFIRMATION_TOPIC = "movement.confirmation";

    private final KafkaTemplate<String, MovementConfirmatedRequestedEvent> kafkaTemplate;

    public KafkaAdapter(KafkaTemplate<String, MovementConfirmatedRequestedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(MovementConfirmatedRequestedEvent request, String movementId) {
        kafkaTemplate.send(
                MOVEMENT_CONFIRMATION_TOPIC,
                movementId,
                request
        ).whenComplete((result, error) -> {
            if (error != null) {
                throw new IllegalStateException(
                        "Could not publish movement confirmation for movement " + movementId,
                        error
                );
            }
        });
    }
}
