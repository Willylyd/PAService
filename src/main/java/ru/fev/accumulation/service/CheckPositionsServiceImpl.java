package ru.fev.accumulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckPositionsRepository;
import ru.fev.accumulation.repository.CheckRepository;

import java.util.List;

@Service
public class CheckPositionsServiceImpl implements CheckPositionsService {

    @Autowired
    private CheckPositionsRepository checkPositionsRepository;

    @Autowired
    private CheckRepository checkRepository;

    @Override
    public void addCheckPosition(CheckPosition checkPosition) {
        checkPositionsRepository.save(checkPosition);

        checkRepository.increaseAmount(checkPosition.getCheckId(), checkPosition.getPosAmount());
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
        return checkPositionsRepository
                .findAll()
                .stream()
                .filter(checkPosition -> checkPosition
                        .getCheckId() == checkId)
                .toList();
    }

    @Override
    public CheckPosition getById(Long id) {
        return checkPositionsRepository.getReferenceById(id);
    }
}
