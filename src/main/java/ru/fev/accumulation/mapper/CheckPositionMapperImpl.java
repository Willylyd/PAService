package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckPositionToDTO;
import ru.fev.accumulation.dto.DTOtoCheckPosition;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckPositionMapperImpl implements CheckPositionMapper {

    @Override
    public CheckPositionToDTO entityToDTO(CheckPosition checkPosition) {

        if(checkPosition == null) {
            return null;
        }

        CheckPositionToDTO checkPositionDTO = new CheckPositionToDTO();

        checkPositionDTO.setId(checkPosition.getId());
        checkPositionDTO.setCheckId(checkPosition.getCheckId());
        checkPositionDTO.setPosAmount(checkPosition.getPosAmount());

        return checkPositionDTO;
    }

    @Override
    public List<CheckPositionToDTO> entitiesToDTO(List<CheckPosition> checkPositions) {

        if(checkPositions == null) {
            return null;
        }

        return checkPositions.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CheckPosition DTOToEntity(DTOtoCheckPosition dtoToCheckPosition) {

        if(dtoToCheckPosition == null) {
            return null;
        }

        CheckPosition checkPosition = new CheckPosition();

        checkPosition.setCheckId(dtoToCheckPosition.getCheckId());
        checkPosition.setPosAmount(dtoToCheckPosition.getPosAmount());

        return checkPosition;
    }
}
