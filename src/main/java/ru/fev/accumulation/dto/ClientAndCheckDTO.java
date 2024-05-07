package ru.fev.accumulation.dto;

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

    private long clientId;
    private int checkId;
    private String cardNumber;
    private BigDecimal amount;
}
