package ru.fev.accumulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CheckToDTO {

    private Long id;
    private Long clientId;
    private BigDecimal amount;
}
