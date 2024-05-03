package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.ClientToDTO;
import ru.fev.accumulation.dto.DTOtoClient;
import ru.fev.accumulation.entity.Client;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientToDTO entityToDTO(Client client);
    List<ClientToDTO> entitiesToDTO(List<Client> clients);

    Client DTOToEntity(DTOtoClient dtoToClient);
}
