package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.entity.Check;

import java.util.List;

@Mapper
public interface CheckMapper {

    CheckDto entityToDTO(Check check);

    List<CheckDto> entitiesToDTO(List<Check> checks);

    Check DTOToEntity(CheckDto checkDto);
}
