package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.ClientDTO;
import ru.fev.accumulation.entity.Client;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientDTO entityToDTO(Client client);
    List<ClientDTO> entitiesToDTO(List<Client> clients);
}
