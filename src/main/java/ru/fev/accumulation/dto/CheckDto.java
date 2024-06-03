package ru.fev.accumulation.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CheckDto {

    @Positive
    private Long id;

    @Positive
    private Long clientId;

    @PositiveOrZero
    private BigDecimal amount;
}
