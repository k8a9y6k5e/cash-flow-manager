package com.krev.finances.infrastructure.grpc;

import com.krev.finances.application.exceptions.ApplicationException;
import com.krev.finances.domain.exceptions.DomainEntityException;
import com.krev.finances.domain.exceptions.DomainException;
import com.krev.finances.infrastructure.exceptions.InfrastructureException;
import com.krev.finances.infrastructure.exceptions.InvalidMovementTypeException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.springframework.context.ApplicationContextException;

@GrpcAdvice
public class GlobalExceptionHandler {
    @GrpcExceptionHandler(ApplicationContextException.class)
    public StatusRuntimeException applicationExceptionHandle(ApplicationException e) {
        return Status.INTERNAL
                .withDescription(e.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(DomainException.class)
    public StatusRuntimeException domainExceptionHandle(DomainException e) {
        return Status.INTERNAL
                .withDescription(e.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(InfrastructureException.class)
    public StatusRuntimeException infrastructureExceptionHandle(InfrastructureException e) {
        return Status.INTERNAL
                .withDescription(e.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(DomainEntityException.class)
    public StatusRuntimeException domainEntityExceptionHandle(DomainEntityException e){
        return Status.INVALID_ARGUMENT
                .withDescription(e.getMessage())
                .asRuntimeException();
    }

    @GrpcExceptionHandler(InvalidMovementTypeException.class)
    public StatusRuntimeException invalidMovementTypeExceptionHandle(InvalidMovementTypeException e) {
        return Status.INVALID_ARGUMENT
                .withDescription(e.getMessage())
                .asRuntimeException();
    }
}
