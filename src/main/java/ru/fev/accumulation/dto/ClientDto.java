package ru.fev.accumulation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    @Positive
    private Long id;

    @Size(min = 20, max = 20)
    @NotNull
    private String cardNumber;

    @PositiveOrZero
    private int discountPoints;
}
