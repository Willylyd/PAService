package ru.fev.accumulation.service;

import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    private CheckRepository checkRepository;

    @Override
    public void addCheck(Check check) {
        checkRepository.save(check);
    }

    @Override
    public Check getById(Long id) {
        return checkRepository.getReferenceById(id);
    }

    @Override
    public void deleteCheck(Long id) {
        checkRepository.deleteById(id);
    }

    @Override
    public List<Check> getByClientId(Long clientId) {
        return checkRepository.getAllByClientId(clientId);
    }

    public List<ClientAndCheckDTO> getAllByCardNumber(String cardNumber) {
        List<Map<String, Object>> checksByCardNumber = checkRepository.getAllByCardNumber(cardNumber);
        List<ClientAndCheckDTO> clientAndCheckDTOs = new ArrayList<>();

        for(Map<String, Object> query : checksByCardNumber) {
            clientAndCheckDTOs.add(new ClientAndCheckDTO(query));
        }
        return clientAndCheckDTOs;
    }

    @Override
    public List<Check> getAll() {
        return checkRepository.findAll();
    }
}
