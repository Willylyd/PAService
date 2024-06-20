package ru.fev.accumulation.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckPositionDto {
    private Long id;
    private Long checkId;
    private BigDecimal posAmount;
}
