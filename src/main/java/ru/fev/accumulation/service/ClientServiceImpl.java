package ru.fev.accumulation.service;

import ru.fev.accumulation.dto.DiscountPointsDTO;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void addClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public int getDiscountPoints(String cardNumber) {
        return clientRepository.getReferenceById(cardNumber).getDiscountPoints();
    }

    @Override
    public void deleteClient(String cardNumber) {
        clientRepository.deleteById(cardNumber);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
