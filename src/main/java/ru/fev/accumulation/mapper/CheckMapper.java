package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckToDTO;
import ru.fev.accumulation.dto.DTOToCheck;
import ru.fev.accumulation.entity.Check;

import java.util.List;

@Mapper
public interface CheckMapper {

    CheckToDTO entityToDTO(Check check);
    List<CheckToDTO> entitiesToDTO(List<Check> checks);

    Check DTOToEntity(DTOToCheck dtoToCheck);
}
