package ru.fev.accumulation.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckDto {
    private Long id;
    private Long clientId;
    private BigDecimal amount;
}
