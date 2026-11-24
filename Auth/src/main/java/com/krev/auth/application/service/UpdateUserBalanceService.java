package com.krev.auth.application.service;

import com.krev.auth.application.port.in.UpdateUserBalanceUseCase;
import com.krev.auth.application.port.out.ProcessedMovementRepository;
import com.krev.auth.application.port.out.UserRepositoryAdapter;
import com.krev.auth.domain.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateUserBalanceService implements UpdateUserBalanceUseCase {
    private final UserRepositoryAdapter userRepository;
    private final ProcessedMovementRepository processedMovementRepository;

    public UpdateUserBalanceService(
            UserRepositoryAdapter userRepository,
            ProcessedMovementRepository processedMovementRepository
    ) {
        this.userRepository = userRepository;
        this.processedMovementRepository = processedMovementRepository;
    }

    @Override
    @Transactional
    public void execute(String movementId, String movementType, Long amount, Long userId) {
        if (movementId == null || movementId.isBlank()) {
            throw new IllegalArgumentException("Movement id is required");
        }

        if (processedMovementRepository.exists(movementId)) {
            return;
        }

        User user = userRepository.findById(userId);
        user.updateBalance(movementType, amount);
        userRepository.save(user);
        processedMovementRepository.markProcessed(movementId);
    }
}
