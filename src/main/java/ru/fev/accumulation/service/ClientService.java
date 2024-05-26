package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Client;

import java.math.BigDecimal;
import java.util.List;

public interface ClientService {

    void addClient(Client client);

    Client getById(Long id);

    Client getByCardNumber(String cardNumber);

    int getDiscountPoints(Long id);

    void deleteClient(Long id);

    List<Client> getAll();

    void subtractDiscountPoints(Long id, int pointsToSubtract);
}
