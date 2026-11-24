package com.krev.auth.application.port.in;

public interface UpdateUserBalanceUseCase {
    void execute(String movementId, String movementType, Long amount, Long userId);
}
