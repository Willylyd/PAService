package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDto entityToDTO(Client client) {

        if (client == null) {
            return null;
        }

        ClientDto clientDTO = new ClientDto();

        clientDTO.setId(client.getId());
        clientDTO.setCardNumber(client.getCardNumber());
        clientDTO.setDiscountPoints(client.getDiscountPoints());

        return clientDTO;
    }

    @Override
    public List<ClientDto> entitiesToDTO(List<Client> clients) {

        if (clients == null) {
            return null;
        }

        return clients.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Client DTOToEntity(ClientDto clientDto) {

        if (clientDto == null) {
            return null;
        }

        Client client = new Client();

        client.setCardNumber(clientDto.getCardNumber());
        client.setDiscountPoints(clientDto.getDiscountPoints());

        return client;
    }
}
