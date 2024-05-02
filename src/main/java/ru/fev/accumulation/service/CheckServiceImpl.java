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
    public void deleteCheck(long id) {
        checkRepository.deleteById(id);
    }

    @Override
    public List<Check> getAll() {
        return checkRepository.findAll();
    }

//    @Override
//    public List<Check> getAllByCardNumber(String cardNumber) {
//        List<Check> checks = checkRepository.findAll();
//
//
//        for(int i = 0; i < checks.size(); ++i) {
//            if(!checks.get(i).getCardNumber().equals(cardNumber)) {
//                checks.remove(i);
//            }
//        }
//        return checks;
//    }




}
