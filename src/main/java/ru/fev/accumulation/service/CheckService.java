package ru.fev.accumulation.service;

import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CheckService {

    void addCheck(Check check);

    Check getById(Long id);

    List<Check> getByClientId(Long clientId);

    List<ClientAndCheckDTO> getAllByCardNumber(String cardNumber);

    void deleteCheck(Long id);

    List<Check> getAll();
}
