package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.CheckPosition;

import java.math.BigDecimal;
import java.util.List;

public interface CheckPositionsService {
    void addCheckPosition(long id, long checkId, BigDecimal posAmount);
    List<CheckPosition> getAll();
    List<CheckPosition> getAllByCheckId(long checkId);
}
