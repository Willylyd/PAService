package ru.fev.accumulation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ClientAndCheckDTO {

    public ClientAndCheckDTO(Map<String, Object> query) {
        clientId = (long) query.get("client_id");
        checkId = (int) query.get("id");
        cardNumber = (String) query.get("card_number");
        amount = (BigDecimal) query.get("amount");
    }

    @Positive
    private long clientId;

    @Positive
    private int checkId;

    @Size(min = 20, max = 20)
    @NotNull
    private String cardNumber;

    @PositiveOrZero
    private BigDecimal amount;
}
