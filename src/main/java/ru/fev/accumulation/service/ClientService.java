package ru.fev.accumulation.service;

import ru.fev.accumulation.dto.DiscountPointsDTO;
import ru.fev.accumulation.entity.Client;

import java.util.List;

public interface ClientService {

    void addClient(Client client);
    int getDiscountPoints(String cardNumber);
    void deleteClient(String cardNumber);
    List<Client> getAll();
}
