package com.krev.finances.infrastructure.adapter.out.kafka;

import com.krev.finances.application.event.MovementConfirmatedRequestedEvent;
import com.krev.finances.application.port.out.MovementConfirmationPublisher;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaAdapter implements MovementConfirmationPublisher {
    private final KafkaTemplate<String, MovementConfirmatedRequestedEvent> kafkaTemplate;

    public KafkaAdapter(KafkaTemplate<String, MovementConfirmatedRequestedEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(MovementConfirmatedRequestedEvent request, String movementId) {
        kafkaTemplate.send(
                "movemente.confirmation",
                movementId,
                request
        );
    }
}
