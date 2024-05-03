package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.ClientToDTO;
import ru.fev.accumulation.dto.DTOToClient;
import ru.fev.accumulation.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientToDTO entityToDTO(Client client) {

        if (client == null) {
            return null;
        }

        ClientToDTO clientDTO = new ClientToDTO();

        clientDTO.setId(client.getId());
        clientDTO.setCardNumber(client.getCardNumber());
        clientDTO.setDiscountPoints(client.getDiscountPoints());

        return clientDTO;
    }

    @Override
    public List<ClientToDTO> entitiesToDTO(List<Client> clients) {

        if (clients == null) {
            return null;
        }

        return clients.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Client DTOToEntity(DTOToClient dtoToClient) {

        if(dtoToClient == null) {
            return null;
        }

        Client client = new Client();

        client.setCardNumber(dtoToClient.getCardNumber());
        client.setDiscountPoints(dtoToClient.getDiscountPoints());

        return client;
    }
}
