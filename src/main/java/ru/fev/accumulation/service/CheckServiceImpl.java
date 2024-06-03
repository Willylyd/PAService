package ru.fev.accumulation.service;

import jakarta.persistence.EntityNotFoundException;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckRepository checkRepository;

    @Override
    public void addCheck(Check check) {
        if (check.getId() < 1 || check.getClientId() < 1) {
            throw new PAIncorrectArgumentException("Incorrect checks parameters");
        }
        try {
            checkRepository.save(check);
        } catch (Exception e) {
            throw new PAIncorrectArgumentException("Incorrect checks parameters");
        }
    }

    @Override
    public Check getById(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("ID must be greater than zero");
        }
        try {
            return checkRepository.getReferenceById(id);
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Check with id {id} not found");
        }
    }

    @Override
    public void deleteCheck(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("ID must be greater than zero");
        }
        try {
            checkRepository.deleteById(id);
        } catch (Exception e) {
            throw new PAIllegalIdException("Incorrect ID");
        }
    }

    @Override
    public List<Check> getByClientId(Long clientId) {
        if (clientId < 1) {
            throw new PAIllegalIdException("ID must be greater than zero");
        }
        try {
            return checkRepository.getAllByClientId(clientId);
        } catch (Exception e) {
            throw new PAIllegalIdException("Incorrect ID");
        }
    }

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
