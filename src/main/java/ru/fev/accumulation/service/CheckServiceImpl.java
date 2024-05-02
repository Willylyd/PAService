package ru.fev.accumulation.service;

import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.repository.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CheckServiceImpl implements CheckService {

    @Autowired
    CheckRepository checkRepository;

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
    public List<Check> getAll() {
        return checkRepository.findAll();
    }
}
