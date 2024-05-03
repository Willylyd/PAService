package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.CheckDTO;
import ru.fev.accumulation.entity.Check;

import java.util.List;

@Mapper
public interface CheckMapper {

    CheckDTO entityToDTO(Check check);
    List<CheckDTO> entitiesToDTO(List<Check> checks);
}
