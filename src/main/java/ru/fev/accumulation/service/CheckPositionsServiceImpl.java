package ru.fev.accumulation.service;

import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
import ru.fev.accumulation.exceptions.PAIllegalIdException;
import ru.fev.accumulation.exceptions.PAIncorrectArgumentException;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;
import ru.fev.accumulation.repository.ClientRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckPositionsServiceImpl implements CheckPositionsService {

    private final int LOW_COEFFICIENT = 50;
    private final int MID_COEFFICIENT = 40;
    private final int HIGH_COEFFICIENT = 30;
    private final long BOTTOM_AMOUNT_LIMIT = 50_000;
    private final long TOP_AMOUNT_LIMIT = 100_000;

    @Autowired
    private CheckPositionsRepository checkPositionsRepository;

    @Autowired
    private CheckRepository checkRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    @Override
    public void addCheckPosition(CheckPosition checkPosition) {

        if (checkPosition.getPosAmount().intValue() < 0) {
            throw new PAIncorrectArgumentException("Amount can't be below zero");
        }

        try {
            var check = checkRepository.getReferenceById(checkPosition.getCheckId());
            checkPositionsRepository.save(checkPosition);
            checkRepository.increaseAmount(check.getId(), checkPosition.getPosAmount());

            // get all client's checks and get their sum
            Long clientId = checkRepository
                    .getReferenceById(checkPosition.getCheckId())
                    .getClientId();
            BigDecimal sumOfAllChecks = checkRepository.getSumOfChecksByClientId(clientId);
            clientRepository.updateDiscountPoints(clientId, getDiscountPoints(sumOfAllChecks));
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Incorrect check ID");
        }
    }

    @Override
    public List<CheckPosition> getAll() {
        return checkPositionsRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCheckPosition(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("Check ID must be greater than zero");
        }

        try {
            Long clientId = checkRepository
                    .getReferenceById(checkPositionsRepository
                            .getReferenceById(id)
                            .getCheckId())
                    .getClientId();

            clientRepository.subtractDiscountPoints(clientId, getDiscountPoints(
                    checkPositionsRepository
                            .getReferenceById(id)
                            .getPosAmount()));
            checkPositionsRepository.deleteById(id);
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Incorrect ID");
        }
    }

    @Override
    public List<CheckPosition> getAllByCheckId(Long checkId) {
        if (checkId < 1) {
            throw new PAIllegalIdException("Check ID must be greater than zero");
        }

        return checkPositionsRepository.getAllByCheckId(checkId);
    }

    @Override
    public CheckPosition getById(Long id) {
        if (id < 1) {
            throw new PAIllegalIdException("ID must be greater than zero");
        }

        try {
            return checkPositionsRepository.getReferenceById(id);
        } catch (Exception e) {
            throw new PAEntityNotFoundException("Incorrect check ID");
        }
    }

    private int getDiscountPoints(BigDecimal amount) {
        if (amount.intValue() <= BOTTOM_AMOUNT_LIMIT) {
            return amount.intValue() / LOW_COEFFICIENT;
        } else if (amount.intValue() <= TOP_AMOUNT_LIMIT) {
            return amount.intValue() / MID_COEFFICIENT;
        } else {
            return amount.intValue() / HIGH_COEFFICIENT;
        }
    }
}
