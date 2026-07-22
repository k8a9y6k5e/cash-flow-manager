package com.krev.finances.application.controller;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.dto.CreateMovementResponseDto;
import com.krev.finances.application.mapper.MovementMapper;
import com.krev.finances.application.port.in.CreateMovementUseCase;
import io.grpc.stub.StreamObserver;

public class MovementGrpcService {

    private final CreateMovementUseCase createMovementUseCase;

    public MovementGrpcService(
            CreateMovementUseCase createMovementUseCase
    ) {
        this.createMovementUseCase = createMovementUseCase;
    }


    public void create(
            CreateMovementDto request,
            StreamObserver<CreateMovementResponseDto> responseObserver
    ){
        MovementMapper.toCreateResponse(
                createMovementUseCase.execute(request)
        );
    }
}
