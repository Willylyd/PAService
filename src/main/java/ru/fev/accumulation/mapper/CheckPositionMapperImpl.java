package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.CheckPositionDto;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckPositionMapperImpl implements CheckPositionMapper {

    @Override
    public CheckPositionDto entityToDTO(CheckPosition checkPosition) {

        if (checkPosition == null) {
            return null;
        }

        CheckPositionDto checkPositionDTO = new CheckPositionDto();

        checkPositionDTO.setId(checkPosition.getId());
        checkPositionDTO.setCheckId(checkPosition.getCheckId());
        checkPositionDTO.setPosAmount(checkPosition.getPosAmount());

        return checkPositionDTO;
    }

    @Override
    public List<CheckPositionDto> entitiesToDTO(List<CheckPosition> checkPositions) {

        if (checkPositions == null) {
            return null;
        }

        return checkPositions.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CheckPosition DTOToEntity(CheckPositionDto checkPositionDto) {

        if (checkPositionDto == null) {
            return null;
        }

        CheckPosition checkPosition = new CheckPosition();

        checkPosition.setCheckId(checkPositionDto.getCheckId());
        checkPosition.setPosAmount(checkPositionDto.getPosAmount());

        return checkPosition;
    }
}
