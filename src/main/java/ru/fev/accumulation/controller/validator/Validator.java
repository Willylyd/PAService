package ru.fev.accumulation.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.service.CheckPositionsService;
import ru.fev.accumulation.service.CheckService;
import ru.fev.accumulation.service.ClientService;

@Component
public class Validator {

    @Autowired
    ClientService clientService;
    @Autowired
    CheckService checkService;
    @Autowired
    CheckPositionsService checkPositionsService;

    public boolean isCardNumberValid(String cardNumber) {
        return clientService.getByCardNumber(cardNumber) != null &&
                cardNumber != null &&
                cardNumber.length() == Client.CARD_NUMBER_LENGTH;
    }

    public boolean isClientIdValid(Long id) {
        return id > 0 && clientService.getById(id) != null;
    }

    public boolean isCheckIdValid(Long id) {
        return id > 0 && checkService.getById(id) != null;
    }

//    public boolean isCheckPositionIdValid(Long id) {
//        return id > 0 && checkPositionsService.getById(id) != null;
//    }
}
