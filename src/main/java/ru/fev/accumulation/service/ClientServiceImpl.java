package ru.fev.accumulation.service;

import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void addClient(Client client) {

        if (client.getCardNumber().length() != Client.CARD_NUMBER_LENGTH
                || client.getCardNumber().isBlank()) {
            throw new PAIncorrectArgumentException("Incorrect card number");
        }
        if (client.getId() < 1) {
            throw new PAIllegalIdException("Id must be greater than zero");
        }
        if (client.getDiscountPoints() < 0) {
            throw new PAIncorrectArgumentException("Discount points should be greater or equal zero");
        }

        clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("Id must be greater than zero");
        }

        try {
            return clientRepository.getReferenceById(id);
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Incorrect ID");
        }
    }

    public Client getByCardNumber(String cardNumber) {

        if (cardNumber.isBlank() || cardNumber.length() != Client.CARD_NUMBER_LENGTH) {
            throw new PAIncorrectArgumentException("Incorrect card number");
        }

        try {
            return clientRepository.getByCardNumber(cardNumber);
        } catch (PersistenceException e) {
            throw new PAEntityNotFoundException("Client with card {cardNumber} not found");
        }
    }

    @Override
    public int getDiscountPoints(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("Check ID must be greater than zero");
        }

        try {
            return clientRepository
                    .getReferenceById(id)
                    .getDiscountPoints();
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Incorrect ID");
        }
    }

    @Override
    public void deleteClient(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("Check ID must be greater than zero");
        }

        clientRepository.deleteById(id);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Transactional
    @Override
    public void subtractDiscountPoints(Long id, int pointsToSubtract) {
        if (id < 1L) {
            throw new PAIllegalIdException("Check ID must be greater than zero");
        }

        if (this.getDiscountPoints(id) < pointsToSubtract) {
           throw new PAIncorrectArgumentException("Incorrect value of 'pointsToSubtract'");
        }

        clientRepository.subtractDiscountPoints(id, pointsToSubtract);
    }
}
