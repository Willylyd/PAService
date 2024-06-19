package ru.fev.accumulation.service;

import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fev.accumulation.repository.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CheckServiceImpl(CheckRepository checkRepository,
                            ClientRepository clientRepository) {
        this.checkRepository = checkRepository;
        this.clientRepository = clientRepository;
    }

    @Override
    public void addCheck(Check check) {
        if (!clientRepository.existsById(check.getClientId())) {
            throw new PAIncorrectArgumentException(String
                    .format("Client with id=%d not found", check.getClientId()));
        }
        checkRepository.save(check);
    }

    @Override
    public Check getById(Long checkId) {
        if (!checkRepository.existsById(checkId)) {
            throw new PAEntityNotFoundException(String
                    .format("Check with id=%d not found", checkId));
        }
        return checkRepository.getReferenceById(checkId);
    }

    @Override
    public void deleteCheck(Long checkId) {
        if (!checkRepository.existsById(checkId)) {
            throw new PAEntityNotFoundException(String
                    .format("Check with id=%d not found", checkId));
        }
        checkRepository.deleteById(checkId);
    }

    @Override
    public List<Check> getAllByClientId(Long clientId) {
        if (!clientRepository.existsById(clientId)) {
            throw new PAEntityNotFoundException(String
                    .format("Client with id=%d not found", clientId));
        }
        return checkRepository.getAllByClientId(clientId);
    }

    @Override
    public List<ClientAndCheckDTO> getAllByCardNumber(String cardNumber) {
        if (cardNumber.isBlank() || cardNumber.length() != Client.CARD_NUMBER_LENGTH) {
            throw new PAIncorrectArgumentException("Incorrect card number");
        }
        var checksByCardNumber = checkRepository.getAllByCardNumber(cardNumber);
        List<ClientAndCheckDTO> clientAndCheckDTOs = new ArrayList<>();

        for (Map<String, Object> query : checksByCardNumber) {
            clientAndCheckDTOs.add(new ClientAndCheckDTO(query));
        }
        return clientAndCheckDTOs;
    }

    @Override
    public List<Check> getAll() {
        return checkRepository.findAll();
    }
}
