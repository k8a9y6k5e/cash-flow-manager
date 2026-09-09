package com.krev.finances.infrastructure.grpc;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.application.port.in.CreateMovementUseCase;
import com.krev.finances.application.port.in.GetMovementPerMonthUseCase;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.infrastructure.grpc.mappers.MovementGrpcMapper;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovementGrpcTestService {
    @Mock
    private StreamObserver<CreateMovementResponse> createResponseObserver;

    @Mock
    private StreamObserver<GetMovementsByMonthResponse> getMovementsByMonthResponseObserver;

    @Mock
    private CreateMovementUseCase createMovementUseCase;

    @Mock
    private MovementGrpcMapper movementGrpcMapper;

    @Mock
    private GetMovementPerMonthUseCase getMovementPerMonthUseCase;

    @InjectMocks
    private MovementGrpcService movementGrpcService;

    @Test
    public void createTest() {
        CreateMovementRequest request = CreateMovementRequest.newBuilder()
                .setType(MovementType.EXPENSE)
                .setAmount(10L)
                .build();

        Movement domainMovement = new Movement(
                com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                10L,
                LocalDate.of(2026, 1, 10)
        );

        when(createMovementUseCase.execute(any(CreateMovementDto.class)))
                .thenReturn(domainMovement);

        CreateMovementDto dto = new CreateMovementDto(
                com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                10L
        );

        when(movementGrpcMapper.toDomain(any(CreateMovementRequest.class)))
                .thenReturn(dto);

        CreateMovementResponse movementResponse = CreateMovementResponse.newBuilder()
                        .setAmount(10L)
                        .setDate(LocalDate.of(2026, 1, 10).toString())
                        .setType(MovementType.EXPENSE)
                        .build();

        when(movementGrpcMapper.toGrpc(any(Movement.class)))
                .thenReturn(movementResponse);

        movementGrpcService.create(request, createResponseObserver);

        ArgumentCaptor<CreateMovementResponse> captor =
                ArgumentCaptor.forClass(CreateMovementResponse.class);

        verify(createResponseObserver).onNext(captor.capture());
        verify(createResponseObserver).onCompleted();
        verify(createResponseObserver, never()).onError(any());
        verify(movementGrpcMapper).toDomain(request);
        verify(movementGrpcMapper).toGrpc(domainMovement);
        verify(createMovementUseCase).execute(dto);

        CreateMovementResponse response = captor.getValue();

        assertEquals(MovementType.EXPENSE, response.getType());
        assertEquals(10L, response.getAmount());
    }

    @Test
    public void getMovementsByMonthTest() {
        GetMovementsByMonthRequest request = GetMovementsByMonthRequest.newBuilder()
                .setMonth(1)
                .setYear(2026)
                .build();

        GetMovementPerMonthDto period = new GetMovementPerMonthDto(
                1,
                2026
        );

        List<Movement> movements = new ArrayList<>();

        com.krev.finances.infrastructure.grpc.Movement movement =
                com.krev.finances.infrastructure.grpc.Movement.newBuilder()
                        .setId(1)
                        .setAmount(10)
                        .setDate(LocalDate.of(2026, 1, 5).toString())
                        .setType(MovementType.EXPENSE)
                        .build();

        movements.add(new Movement(
                com.krev.finances.domain.valueobject.MovementType.EXPENSE,
                10L
        ));

        GetMovementsByMonthResponse movementsResponse = GetMovementsByMonthResponse.newBuilder()
                        .addMovements(movement)
                        .build();

        when(movementGrpcMapper.toPeriod(any(GetMovementsByMonthRequest.class)))
                .thenReturn(period);

        when(getMovementPerMonthUseCase.execute(any(GetMovementPerMonthDto.class)))
                .thenReturn(movements);

        when(movementGrpcMapper.toGrpc(any(List.class)))
                .thenReturn(movementsResponse);

        movementGrpcService.getMovementsByMonth(request, getMovementsByMonthResponseObserver);

        ArgumentCaptor<GetMovementsByMonthResponse> captor =
                ArgumentCaptor.forClass(GetMovementsByMonthResponse.class);

        verify(getMovementsByMonthResponseObserver).onNext(captor.capture());
        verify(getMovementsByMonthResponseObserver).onCompleted();
        verify(getMovementsByMonthResponseObserver, never()).onError(any());
        verify(movementGrpcMapper).toPeriod(request);
        verify(movementGrpcMapper).toGrpc(movements);
        verify(getMovementPerMonthUseCase).execute(period);

        GetMovementsByMonthResponse response = captor.getValue();

        assertEquals(
                MovementType.EXPENSE,
                response.getMovements(0).getType()
                );
        assertEquals(10L, response.getMovements(0).getAmount());
    }
}
