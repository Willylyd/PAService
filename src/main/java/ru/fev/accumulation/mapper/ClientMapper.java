package ru.fev.accumulation.mapper;

import org.mapstruct.Mapper;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientDto entityToDTO(Client client);

    List<ClientDto> entitiesToDTO(List<Client> clients);

    Client DTOToEntity(ClientDto clientDto);
}
