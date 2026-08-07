package com.krev.finances.application.usecase;

import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.application.port.in.GetMovementPerMonthUseCase;
import com.krev.finances.domain.entity.Movement;
import com.krev.finances.domain.ports.MovementRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GetMovementPerMonthService implements GetMovementPerMonthUseCase {
    private final MovementRepositoryPort repositoryPort;

    public GetMovementPerMonthService(MovementRepositoryPort repositoryPort){
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Movement> execute(GetMovementPerMonthDto body) {
        Integer year =
                body.year() != null && body.year() != 0 ? body.year() : LocalDate.now().getYear();

        LocalDate initialDate = createInitialDate(body.month(), year);
        LocalDate finalDate = createFinalDate(body.month(), year);

        return repositoryPort.findByDateBetween(initialDate, finalDate);
    }

    private LocalDate createInitialDate(Integer month, Integer year) {
        return LocalDate.of(year, month, 1);
    }

    private LocalDate createFinalDate(Integer month, Integer year) {
        LocalDate date = LocalDate.of(year, month, 1);

        return date
                .plusMonths(1)
                .minusDays(1);
    }
}
