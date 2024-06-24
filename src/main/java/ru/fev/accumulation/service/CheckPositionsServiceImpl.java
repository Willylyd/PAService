package ru.fev.accumulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.exceptions.PAEntityNotFoundException;
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

    private final CheckPositionsRepository checkPositionsRepository;
    private final CheckRepository checkRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public CheckPositionsServiceImpl(CheckPositionsRepository checkPositionsRepository,
                                     CheckRepository checkRepository,
                                     ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.checkRepository = checkRepository;
        this.checkPositionsRepository = checkPositionsRepository;
    }

    @Transactional
    @Override
    public void addCheckPosition(CheckPosition checkPosition) {
        if (checkPosition.getPosAmount().intValue() < 0) {
            throw new PAIncorrectArgumentException("Amount can't be below zero");
        }

        if (!checkRepository.existsById(checkPosition.getCheckId())) {
            throw new PAIncorrectArgumentException(String
                    .format("Check with id=%d not found", checkPosition.getCheckId()));
        }

        var check = checkRepository.getReferenceById(checkPosition.getCheckId());
        checkPositionsRepository.save(checkPosition);
        checkRepository.increaseAmount(check.getId(), checkPosition.getPosAmount());

        var clientId = check.getClientId();
        var sumOfAllChecks = checkRepository.getSumOfChecksByClientId(clientId);
        clientRepository.updateDiscountPoints(clientId, getDiscountPoints(sumOfAllChecks));
    }

    @Override
    public List<CheckPosition> getAll() {
        return checkPositionsRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteCheckPosition(Long checkPositionId) {
        if (!checkPositionsRepository.existsById(checkPositionId)) {
            throw new PAEntityNotFoundException(String
                    .format("Check position with id=%d not found", checkPositionId));
        }
        Long clientId = checkRepository
                .getReferenceById(checkPositionsRepository
                        .getReferenceById(checkPositionId)
                        .getCheckId())
                .getClientId();

        clientRepository.subtractDiscountPoints(clientId, getDiscountPoints(
                checkPositionsRepository
                        .getReferenceById(checkPositionId)
                        .getPosAmount()));
        checkPositionsRepository.deleteById(checkPositionId);
    }

    @Override
    public void deleteAllByCheckId(Long check_id) {
        checkPositionsRepository.deleteAllByCheckId(check_id);
    }

    @Override
    public List<CheckPosition> getAllByCheckId(Long checkId) {
        if (!checkRepository.existsById(checkId)) {
            throw new PAEntityNotFoundException(String
                    .format("Check with id=%d not found", checkId));
        }
        return checkPositionsRepository.getAllByCheckId(checkId);
    }

    @Override
    public CheckPosition getById(Long checkPositionId) {
        if (!checkPositionsRepository.existsById(checkPositionId)) {
            throw new PAEntityNotFoundException(String
                    .format("Check position with id=%d not found", checkPositionId));
        }
        return checkPositionsRepository.getReferenceById(checkPositionId);
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
