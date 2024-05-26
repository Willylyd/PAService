package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.entity.Check;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckMapperImpl implements CheckMapper {

    @Override
    public CheckDto entityToDTO(Check check) {

        if (check == null) {
            return null;
        }

        CheckDto checkDTO = new CheckDto();

        checkDTO.setId(check.getId());
        checkDTO.setClientId(check.getClientId());
        checkDTO.setAmount(check.getAmount());

        return checkDTO;
    }

    @Override
    public List<CheckDto> entitiesToDTO(List<Check> checks) {

        if (checks == null) {
            return null;
        }

        return checks.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Check DTOToEntity(CheckDto checkDto) {

        if (checkDto == null) {
            return null;
        }

        Check check = new Check();
        check.setClientId(checkDto.getClientId());

        return check;
    }
}
