package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;

public interface CheckPositionsService {
    void addCheckPosition(CheckPosition checkPosition);

    List<CheckPosition> getAllByCheckId(Long checkId);

    List<CheckPosition> getAll();
}
