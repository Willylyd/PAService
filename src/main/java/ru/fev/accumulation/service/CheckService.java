package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Check;

import java.math.BigDecimal;
import java.util.List;

public interface CheckService {

    void addCheck(Check check);
//    void updateCheckAmount(Check check, BigDecimal amount);
    void deleteCheck(long id);
    List<Check> getAll();
//    List<Check> getAllByCardNumber(String cardNumber);
//    BigDecimal getChecksSumByCardNumber(String cardNumber);
//    BigDecimal getCheckPositionsSum(Check check);
}
