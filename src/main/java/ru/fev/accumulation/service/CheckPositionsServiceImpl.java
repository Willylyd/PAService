package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.CheckPosition;

import java.math.BigDecimal;
import java.util.List;

public class CheckPositionsServiceImpl implements CheckPositionsService {

    @Override
    public void addCheckPosition(long id, long checkId, BigDecimal posAmount) {

    }

    @Override
    public List<CheckPosition> getAll() {
        return List.of();
    }

    @Override
    public List<CheckPosition> getAllByCheckId(long checkId) {
        return List.of();
    }
}
