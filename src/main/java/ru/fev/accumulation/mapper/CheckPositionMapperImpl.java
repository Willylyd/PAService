package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckDTO;
import ru.fev.accumulation.dto.CheckPositionDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckPositionMapperImpl implements CheckPositionMapper {

    @Override
    public CheckPositionDTO entityToDTO(CheckPosition checkPosition) {

        if(checkPosition == null) {
            return null;
        }

        CheckPositionDTO checkPositionDTO = new CheckPositionDTO();

        checkPositionDTO.setId(checkPosition.getId());
        checkPositionDTO.setCheckId(checkPosition.getCheckId());
        checkPositionDTO.setPosAmount(checkPosition.getPosAmount());

        return checkPositionDTO;
    }

    @Override
    public List<CheckPositionDTO> entitiesToDTO(List<CheckPosition> checkPositions) {

        if(checkPositions == null) {
            return null;
        }

        return checkPositions.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
