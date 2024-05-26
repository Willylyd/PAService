package ru.fev.accumulation.controller.validator;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.service.CheckPositionsService;
import ru.fev.accumulation.service.CheckService;
import ru.fev.accumulation.service.ClientService;

@Component
public class Validator {

    public boolean isCardNumberValid(ClientService clientService, String cardNumber) {
        return clientService.getByCardNumber(cardNumber) != null &&
                cardNumber != null &&
                cardNumber.length() == Client.CARD_NUMBER_LENGTH;
    }

    public boolean isClientIdValid(ClientService clientService, Long id) {
        return id > 0 && clientService.getById(id) != null;
    }

    public boolean isCheckIdValid(CheckService checkService, Long id) {
        return id > 0 && checkService.getById(id) != null;
    }

    public boolean isCheckPositionIdValid(CheckPositionsService checkPositionsService, Long id) {
        return id > 0 && checkPositionsService.getById(id) != null;
    }
}
