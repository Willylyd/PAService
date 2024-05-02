package ru.fev.accumulation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckPositionsRepository;

import java.util.List;

@Service
public class CheckPositionsServiceImpl implements CheckPositionsService {

    @Autowired
    private CheckPositionsRepository checkPositionsRepository;

    @Override
    public void addCheckPosition(CheckPosition checkPosition) {
        checkPositionsRepository.save(checkPosition);
    }

    @Override
    public List<CheckPosition> getAll() {
        return checkPositionsRepository.findAll();
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
}
