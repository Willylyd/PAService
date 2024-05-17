package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void addClient(Client client) {

        if(client.getCardNumber().length() != 20) {
            throw new InvalidParameterException("Incorrect card number");
        }

        clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.getReferenceById(id);
    }

    public Client getByCardNumber(String cardNumber) {
        return clientRepository.getByCardNumber(cardNumber);
    }

    @Override
    public int getDiscountPoints(Long id) {
        return clientRepository.getReferenceById(id).getDiscountPoints();
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }
}
