package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckPositionToDTO;
import ru.fev.accumulation.dto.DTOtoCheckPosition;
import ru.fev.accumulation.entity.CheckPosition;

import java.util.List;

@Mapper
public interface CheckPositionMapper {

    CheckPositionToDTO entityToDTO(CheckPosition checkPosition);
    List<CheckPositionToDTO> entitiesToDTO(List<CheckPosition> checkPositions);

    CheckPosition DTOToEntity(DTOtoCheckPosition dtoToCheckPosition);
}
