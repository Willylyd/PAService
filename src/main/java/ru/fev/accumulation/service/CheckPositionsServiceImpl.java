package ru.fev.accumulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;
import ru.fev.accumulation.repository.ClientRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckPositionsServiceImpl implements CheckPositionsService {

    @Autowired
    private CheckPositionsRepository checkPositionsRepository;

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void addCheckPosition(CheckPosition checkPosition) {

        // add check pos
        checkPositionsRepository.save(checkPosition);

        // update check amount
        checkRepository.increaseAmount(checkPosition.getCheckId(), checkPosition.getPosAmount());

        // get all client's checks and get their sum
        Long clientId = checkRepository.getReferenceById(checkPosition.getCheckId()).getClientId();
        BigDecimal sumOfAllChecks = checkRepository.getSumOfChecksByClientId(clientId);
        clientRepository.updateDiscountPoints(clientId, getDiscountPoints(sumOfAllChecks));
    }

    @Override
    public List<CheckPosition> getAll() {
        return checkPositionsRepository.findAll();
    }

    @Override
    public void deleteCheckPosition(Long id) {
        checkPositionsRepository.deleteById(id);
    }

    @Override
    public List<CheckPosition> getAllByCheckId(Long checkId) {
        return checkPositionsRepository.getAllByCheckId(checkId);
    }

    @Override
    public CheckPosition getById(Long id) {
        return checkPositionsRepository.getReferenceById(id);
    }

    private int getDiscountPoints(BigDecimal amount) {
        if (amount.intValue() <= 50_000) {
            return amount.intValue() / 50;
        } else if (amount.intValue() <= 100_000) {
            return amount.intValue() / 40;
        } else {
            return amount.intValue() / 30;
        }
    }
}
