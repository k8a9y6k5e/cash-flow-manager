package com.krev.finances.infrastructure.grpc.mappers;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.infrastructure.grpc.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MovementGrpcTestMapper {
    private final MovementGrpcMapper mapper = new MovementGrpcMapper();

    @Test
    public void toDomainTest() {
        CreateMovementRequest request = CreateMovementRequest.newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10L)
                .build();

        CreateMovementDto movement = new CreateMovementDto(
                com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                10L
        );

        CreateMovementDto response = mapper.toDomain(request);

        assertEquals(movement, response);
        assertEquals(com.krev.finances.domain.valueobject.MovementType.EXPENSE, response.type());
        assertEquals(10L, response.amount());
    }

    @Test
    public void toGrpcTest() {
        Movement request = new Movement(
                1L,
                com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                10L,
                LocalDate.of(2026, 01, 01)
        );

        CreateMovementResponse movementResponse = CreateMovementResponse
                .newBuilder()
                .setId(1L)
                .setType(MovementType.EXPENSE)
                .setAmount(10L)
                .setDate(
                        LocalDate.of(2026, 01, 01).toString()
                )
                .build();

        CreateMovementResponse response = mapper.toGrpc(request);

        assertEquals(movementResponse, response);
        assertEquals(MovementType.EXPENSE, response.getType());
        assertEquals(10L, response.getAmount());
        assertEquals(LocalDate.of(2026, 01, 01).toString(), response.getDate());
    }

    @Test
    public void toPeriodTest() {
        GetMovementsByMonthRequest request = GetMovementsByMonthRequest
                .newBuilder()
                .setYear(2026)
                .setMonth(01)
                .build();

        GetMovementPerMonthDto period = new GetMovementPerMonthDto(
                01,
                2026
        );

        GetMovementPerMonthDto response = mapper.toPeriod(request);

        assertEquals(period, response);
        assertEquals(01, response.month());
        assertEquals(2026, response.year());
    }

    @Test
    public void toListedGrpcTest() {
        List<Movement> request = new ArrayList<>();

        request.add(
                new Movement(
                        1L,
                        com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                        10L,
                        LocalDate.of(2026, 01, 01)
                )
        );

        GetMovementsByMonthResponse listedMovements = GetMovementsByMonthResponse
                .newBuilder()
                .addMovements(
                        com.krev.finances.infrastructure.grpc.Movement
                                .newBuilder()
                                .setId(1L)
                                .setType(MovementType.EXPENSE)
                                .setAmount(10L)
                                .setDate(LocalDate.of(2026, 01, 01).toString())
                                .build()
                )
                .build();

        GetMovementsByMonthResponse response = mapper.toGrpc(request);

        assertEquals(listedMovements, response);
        assertEquals(MovementType.EXPENSE, response.getMovements(0).getType());
        assertEquals(10L, response.getMovements(0).getAmount());
        assertEquals(
                LocalDate.of(2026, 01, 01).toString(),
                response.getMovements(0).getDate()
        );
    }
}
