package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckDTO;
import ru.fev.accumulation.dto.CheckPositionDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;

@Mapper
public interface CheckPositionMapper {

    CheckPositionDTO entityToDTO(CheckPosition checkPosition);
    List<CheckPositionDTO> entitiesToDTO(List<CheckPosition> checkPositions);
}
