package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckDTO;
import ru.fev.accumulation.entity.Check;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckMapperImpl implements CheckMapper {

    @Override
    public CheckDTO entityToDTO(Check check) {

        if(check == null) {
            return null;
        }

        CheckDTO checkDTO = new CheckDTO();

        checkDTO.setId(check.getId());
        checkDTO.setCardNumber(check.getCardNumber());
        checkDTO.setAmount(check.getAmount());

        return checkDTO;
    }

    @Override
    public List<CheckDTO> entitiesToDTO(List<Check> checks) {

        if(checks == null) {
            return null;
        }

        return checks.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
