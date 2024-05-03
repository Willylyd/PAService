package ru.fev.accumulation.mapper;

import org.springframework.stereotype.Component;
import ru.fev.accumulation.dto.ClientDTO;
import ru.fev.accumulation.entity.Client;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public ClientDTO entityToDTO(Client client) {

        if (client == null) {
            return null;
        }

        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setId(client.getId());
        clientDTO.setCardNumber(client.getCardNumber());
        clientDTO.setDiscountPoints(client.getDiscountPoints());

        return clientDTO;
    }

    @Override
    public List<ClientDTO> entitiesToDTO(List<Client> clients) {

        if (clients == null) {
            return null;
        }

        return clients.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }
}
