package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckPositionDto;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;

@Mapper
public interface CheckPositionMapper {

    CheckPositionDto entityToDTO(CheckPosition checkPosition);

    List<CheckPositionDto> entitiesToDTO(List<CheckPosition> checkPositions);

    CheckPosition DTOToEntity(CheckPositionDto checkPositionDto);
}
