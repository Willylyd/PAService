package ru.fev.accumulation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClientDto {

    private Long id;

    @Size(min = 20, max = 20)
    @NotNull
    private String cardNumber;

    @PositiveOrZero
    private int discountPoints;
}
