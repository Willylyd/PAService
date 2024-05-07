package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.entity.Client;

import java.util.List;

public interface CheckPositionsService {
    void addCheckPosition(CheckPosition checkPosition);

    List<CheckPosition> getAllByCheckId(Long checkId);

    CheckPosition getById(Long id);

    List<CheckPosition> getAll();

    void deleteCheckPosition(Long id);
}
