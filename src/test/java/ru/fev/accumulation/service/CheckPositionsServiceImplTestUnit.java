package ru.fev.accumulation.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckPositionsServiceImplTestUnit {
    private CheckPositionsServiceImpl checkPositionsService;
    private Method method;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        checkPositionsService = new CheckPositionsServiceImpl();
        method = ReflectionUtils.findMethod(CheckPositionsServiceImpl.class
                        , "getDiscountPoints"
                        , BigDecimal.class)
                .orElseThrow(() -> new NoSuchMethodException("Method not found"));
    }

    @Test
    void getDiscountPointsBefore50k() {
        int amount = 2350;
        int expected = amount / 50;

        int result = (int) ReflectionUtils.invokeMethod(method, checkPositionsService, BigDecimal.valueOf(amount));

        assertEquals(expected, result);
    }

    @Test
    void getDiscountPointsBefore100k() {
        int amount = 73855;
        int expected = amount / 40;

        int result = (int) ReflectionUtils.invokeMethod(method, checkPositionsService, BigDecimal.valueOf(amount));

        assertEquals(expected, result);
    }

    @Test
    void getDiscountPointsAbove100k() {
        int amount = 100001;
        int expected = amount / 30;

        int result = (int) ReflectionUtils.invokeMethod(method, checkPositionsService, BigDecimal.valueOf(amount));

        assertEquals(expected, result);
    }

}
