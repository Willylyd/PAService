package ru.fev.accumulation.service;

import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;
import ru.fev.accumulation.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final CheckRepository checkRepository;
    private final CheckPositionsRepository checkPositionsRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository,
                             CheckRepository checkRepository,
                             CheckPositionsRepository checkPositionsRepository) {
        this.clientRepository = clientRepository;
        this.checkRepository = checkRepository;
        this.checkPositionsRepository = checkPositionsRepository;
    }

    @Override
    public void addClient(Client client) {
        if (client.getCardNumber().isBlank() ||
                client.getCardNumber().length() != Client.CARD_NUMBER_LENGTH) {
            throw new PAIncorrectArgumentException("Incorrect card number");
        }
        if (clientRepository.existsByCardNumber(client.getCardNumber())) {
            throw new PAIncorrectArgumentException(String
                    .format("Client with card number=%s already exists", client.getCardNumber()));
        }
        if (client.getDiscountPoints() < 0) {
            throw new PAIncorrectArgumentException("Discount points should be greater or equal zero");
        }

        clientRepository.save(client);
    }

    @Override
    public Client getById(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with id=%d not found", clientId));
        }

        return clientRepository.getReferenceById(clientId);
    }

    @Override
    public Client getByCardNumber(String cardNumber) {
        if (cardNumber.isBlank() || cardNumber.length() != Client.CARD_NUMBER_LENGTH) {
            throw new PAIncorrectArgumentException("Incorrect card number");
        }

        if (!clientRepository.existsByCardNumber(cardNumber)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with card number=%s not found", cardNumber));
        }
        return clientRepository.getByCardNumber(cardNumber);
    }

    @Override
    public int getDiscountPoints(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with id=%d not found", clientId));
        }
        return clientRepository
                .getReferenceById(clientId)
                .getDiscountPoints();
    }

    @Override
    public void deleteClient(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with id=%d not found", clientId));
        }
        var checks = checkRepository.findAll();
        for(Check check : checks) {
            checkPositionsRepository.deleteAllByCheckId(check.getId());
        }
        for(Check check : checks) {
            checkRepository.deleteById(check.getId());
        }
        clientRepository.deleteById(clientId);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Transactional
    @Override
    public void subtractDiscountPoints(Long clientId, int pointsToSubtract) {
        if (!clientRepository.existsById(clientId)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with id=%d not found", clientId));
        }
        if (this.getDiscountPoints(clientId) < pointsToSubtract) {
            throw new PAIncorrectArgumentException("Incorrect value of 'points to subtract'");
        }

        clientRepository.subtractDiscountPoints(clientId, pointsToSubtract);
    }
}
