package com.krev.finances.infrastructure.grpc.mappers;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.valueobject.MovementType;
import com.krev.finances.infrastructure.grpc.CreateMovementRequest;
import com.krev.finances.infrastructure.grpc.CreateMovementResponse;
import org.springframework.stereotype.Component;

@Component
public class MovementGrpcMapper {
    public CreateMovementDto toDomain(CreateMovementRequest request) {
        return new CreateMovementDto(
                toDomainType(request.getType()),
                request.getAmount()
        );
    }

    private MovementType toDomainType(
            com.krev.finances.infrastructure.grpc.MovementType type
    ){
        switch (type) {
            case INCOME :
                return MovementType.INCOME;

            case EXPENSE:
                return MovementType.EXPENSE;

        }

        return null;//change to an exception throw
    }

    public CreateMovementResponse toGrpc(Movement movement) {
        return CreateMovementResponse.newBuilder()
                .setId(movement.getId())
                .setType(toGrpcType(movement.getType()))
                .setAmount(movement.getAmount())
                .build();
    }

    private com.krev.finances.infrastructure.grpc.MovementType toGrpcType(
            MovementType type
    ) {
        switch (type){
            case INCOME:
                return com.krev.finances.infrastructure.grpc.MovementType.INCOME;

            case EXPENSE:
                return com.krev.finances.infrastructure.grpc.MovementType.EXPENSE;

        }
        return null;//change to an exception throw
    }
}
