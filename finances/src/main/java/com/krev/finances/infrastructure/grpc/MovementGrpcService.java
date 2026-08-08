package com.krev.finances.infrastructure.grpc;

import com.krev.finances.application.port.in.CreateMovementUseCase;
import com.krev.finances.application.port.in.GetMovementPerMonthUseCase;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.infrastructure.grpc.mappers.MovementGrpcMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class MovementGrpcService extends MovementServiceGrpc.MovementServiceImplBase {

    private final CreateMovementUseCase createMovementUseCase;

    private final MovementGrpcMapper movementGrpcMapper;

    private final GetMovementPerMonthUseCase getMovementPerMonthUseCase;

    public MovementGrpcService(
            CreateMovementUseCase createMovementUseCase,
            MovementGrpcMapper movementGrpcMapper,
            GetMovementPerMonthUseCase getMovementPerMonthUseCase
    ) {
        this.createMovementUseCase = createMovementUseCase;
        this.movementGrpcMapper = movementGrpcMapper;
        this.getMovementPerMonthUseCase = getMovementPerMonthUseCase;
    }

    @Override
    public void create(
            CreateMovementRequest request,
            StreamObserver<CreateMovementResponse> responseObserver
    ) {
        Movement response = createMovementUseCase.execute(
                movementGrpcMapper.toDomain(request)
        );

        responseObserver.onNext(
                movementGrpcMapper.toGrpc(response)
        );

        responseObserver.onCompleted();
    }

    @Override
    public void getMovementsByMonth(
            GetMovementsByMonthRequest request,
            StreamObserver<GetMovementsByMonthResponse> responseObserver
    ) {
        List<Movement> response = getMovementPerMonthUseCase.execute(
                movementGrpcMapper.toPeriod(request)
        );

        responseObserver.onNext(
            movementGrpcMapper.toGrpc(response)
        );

        responseObserver.onCompleted();
    }
}
