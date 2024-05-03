package ru.fev.accumulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientToDTO {

    private Long id;
    private String cardNumber;
    private int discountPoints;
}