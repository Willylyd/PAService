package ru.fev.accumulation.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.mapper.ClientMapper;
import ru.fev.accumulation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/clients")
public class ClientRestController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    @Autowired
    public ClientRestController(ClientService clientService,
                                ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping("/points/{id}")
    public ResponseEntity<Integer> getDiscountPoints(@PathVariable("id") Long id) {
        int discountPoints = this.clientService.getDiscountPoints(id);
        return ResponseEntity.ok(discountPoints);
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto,
                                               UriComponentsBuilder uriComponentsBuilder) {
        Client client = this.clientMapper.DTOToEntity(clientDto);
        this.clientService.addClient(client);
        return ResponseEntity.ok(this.clientMapper.entityToDTO(client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") Long id) {
        Client client = this.clientService.getById(id);
        return ResponseEntity.ok(this.clientMapper.entityToDTO(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<Client> clients = this.clientService.getAll();
        if (clients.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(this.clientMapper.entitiesToDTO(clients));
    }

    @GetMapping("/cardnumber/{card_number}")
    public ResponseEntity<ClientDto> getByCardNumber(@PathVariable("card_number") String cardNumber) {
        Client client = this.clientService.getByCardNumber(cardNumber);
        return ResponseEntity.ok(this.clientMapper.entityToDTO(client));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> subtractDiscountPoints(@PathVariable("id") Long id,
                                                            @RequestParam("points") int pointsToSubtract) {
        clientService.subtractDiscountPoints(id, pointsToSubtract);
        ClientDto clientDto = clientMapper.entityToDTO(clientService.getById(id));
        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
