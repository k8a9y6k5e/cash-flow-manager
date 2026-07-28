package com.krev.finances.infrastructure.grpc;

import com.krev.finances.application.port.in.CreateMovementUseCase;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.infrastructure.grpc.mappers.MovementGrpcMapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class MovementGrpcService extends MovementServiceGrpc.MovementServiceImplBase {

    private final CreateMovementUseCase createMovementUseCase;

    private final MovementGrpcMapper movementGrpcMapper;

    public MovementGrpcService(
            CreateMovementUseCase createMovementUseCase,
            MovementGrpcMapper movementGrpcMapper
    ) {
        this.createMovementUseCase = createMovementUseCase;
        this.movementGrpcMapper = movementGrpcMapper;
    }

    @Override
    public void create(
            CreateMovementRequest request,
            StreamObserver<CreateMovementResponse> responseObserver
    ) {
        try {
            Movement response = createMovementUseCase.execute(
                    movementGrpcMapper.toDomain(request)
            );

            responseObserver.onNext(
                    movementGrpcMapper.toGrpc(response)
            );

            responseObserver.onCompleted();
        }
        catch (Exception ex) {
            responseObserver.onError(ex);
        }
    }
}
