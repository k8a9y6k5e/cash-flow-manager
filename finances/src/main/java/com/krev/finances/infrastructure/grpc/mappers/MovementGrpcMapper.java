package com.krev.finances.infrastructure.grpc.mappers;

import com.krev.finances.application.dto.CreateMovementDto;
import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.valueobject.MovementType;
import com.krev.finances.infrastructure.exceptions.InvalidMovementTypeException;
import com.krev.finances.infrastructure.grpc.CreateMovementRequest;
import com.krev.finances.infrastructure.grpc.CreateMovementResponse;
import com.krev.finances.infrastructure.grpc.GetMovementsByMonthRequest;
import com.krev.finances.infrastructure.grpc.GetMovementsByMonthResponse;
import org.springframework.stereotype.Component;

import java.util.List;

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

        throw new InvalidMovementTypeException("Invalid Movement Type passed");
    }

    public CreateMovementResponse toGrpc(Movement movement) {
        return CreateMovementResponse.newBuilder()
                .setId(movement.getId())
                .setType(toGrpcType(movement.getType()))
                .setAmount(movement.getAmount())
                .setDate(movement.getDate().toString())
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

        throw new InvalidMovementTypeException("Invalid Movement Type passed");
    }

    public GetMovementPerMonthDto toPeriod(GetMovementsByMonthRequest request) {
        return new GetMovementPerMonthDto(
                request.getMonth(),
                request.getYear()
        );
    }

    private com.krev.finances.infrastructure.grpc.Movement toGrpcMovement(Movement movement){
        return com.krev.finances.infrastructure.grpc.Movement.newBuilder()
                .setId(movement.getId())
                .setType(toGrpcType(movement.getType()))
                .setAmount(movement.getAmount())
                .setDate(movement.getDate().toString())
                .build();
    }

    public GetMovementsByMonthResponse toGrpc(List<Movement> movements) {
        GetMovementsByMonthResponse.Builder builder = GetMovementsByMonthResponse.newBuilder();

        for(Movement movement : movements) {
            builder.addMovements(toGrpcMovement(movement));
        }

        return builder.build();
    }
}
