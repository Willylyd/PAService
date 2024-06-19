package ru.fev.accumulation.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String cardNumber;
    private int discountPoints;
}
