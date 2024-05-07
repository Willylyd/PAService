package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckToDTO;
import ru.fev.accumulation.dto.DTOtoCheck;
import ru.fev.accumulation.entity.Check;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckMapperImpl implements CheckMapper {

    @Override
    public CheckToDTO entityToDTO(Check check) {

        if(check == null) {
            return null;
        }

        CheckToDTO checkDTO = new CheckToDTO();

        checkDTO.setId(check.getId());
        checkDTO.setClientId(check.getClientId());
        checkDTO.setAmount(check.getAmount());

        return checkDTO;
    }

    @Override
    public List<CheckToDTO> entitiesToDTO(List<Check> checks) {

        if(checks == null) {
            return null;
        }

        return checks.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Check DTOToEntity(DTOtoCheck dtoToCheck) {

        if(dtoToCheck == null) {
            return null;
        }

        Check check = new Check();

        check.setClientId(dtoToCheck.getClientId());

        return check;
    }
}
