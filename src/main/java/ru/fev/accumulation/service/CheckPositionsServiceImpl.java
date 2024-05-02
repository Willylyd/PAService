package ru.fev.accumulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.fev.accumulation.controller.CheckPositionRestController;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckPositionsRepository;

import java.math.BigDecimal;
import java.util.List;

public class CheckPositionsServiceImpl implements CheckPositionsService {

    @Autowired
    CheckPositionsRepository checkPositionsRepository;

    @Override
    public void addCheckPosition(CheckPosition checkPosition) {
        checkPositionsRepository.save(checkPosition);
    }

    @Override
    public List<CheckPosition> getAll() {
        return List.of();
    }

    @Override
    public List<CheckPosition> getAllByCheckId(Long checkId) {
        return List.of();
    }
}
