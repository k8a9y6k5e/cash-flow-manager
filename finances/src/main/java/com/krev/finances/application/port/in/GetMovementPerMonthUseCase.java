package com.krev.finances.application.port.in;

import com.krev.finances.application.dto.GetMovementPerMonthDto;
import com.krev.finances.domain.entity.Movement;

import java.util.List;

public interface GetMovementPerMonthUseCase {
    List<Movement> execute(GetMovementPerMonthDto body);
}
