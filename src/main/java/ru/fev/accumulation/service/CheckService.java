package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Check;

import java.math.BigDecimal;
import java.util.List;

public interface CheckService {

    void addCheck(Check check);

    Check getById(Long id);

    void deleteCheck(Long id);

    List<Check> getAll();
}
