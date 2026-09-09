package com.krev.finances.e2e;

import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import com.krev.finances.infrastructure.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
public class MovementsE2ETest {
    private ManagedChannel channel;
    private MovementServiceGrpc.MovementServiceBlockingStub client;

    private MovementRepositoryPort movementRepositoryPort;

    @Autowired
    public MovementsE2ETest(
            MovementRepositoryPort movementRepositoryPort
    ) {
        this.movementRepositoryPort = movementRepositoryPort;
    }

    @BeforeEach
    public void setup() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        client = MovementServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    public void teardown() throws InterruptedException {
        channel.shutdown();
        channel.awaitTermination(5, TimeUnit.SECONDS);
    }

    @Test
    public void shouldCreateMovement() {
        CreateMovementRequest request = CreateMovementRequest
                .newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10)
                .build();

        CreateMovementResponse response = client.create(request);

        assertNotNull(response);
        assertTrue(response.getId() > 0);
        assertEquals(MovementType.EXPENSE, response.getType());
        assertEquals(10, response.getAmount());
    }

    @Test
    public void shouldGetMovementsFilteredByMonth() {
        movementRepositoryPort.save(
                new Movement(
                        com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                        10L,
                        LocalDate.of(2010, 1,1)
                )
        );

        GetMovementsByMonthRequest request = GetMovementsByMonthRequest
                .newBuilder()
                .setMonth(1)
                .setYear(2010)
                .build();

        GetMovementsByMonthResponse response = client.getMovementsByMonth(request);

        assertNotNull(response);
        assertTrue(!response.getMovementsList().isEmpty());
    }
}
