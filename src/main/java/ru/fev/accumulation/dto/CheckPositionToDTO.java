package ru.fev.accumulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class CheckPositionToDTO {

    private Long id;
    private Long checkId;
    private BigDecimal posAmount;
}
