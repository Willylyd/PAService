package ru.fev.accumulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CheckDTO {

    private Long id;
    private String cardNumber;
    private BigDecimal amount;
}
