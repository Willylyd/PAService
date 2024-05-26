package ru.fev.accumulation.controller;

import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.mapper.ClientMapper;
import ru.fev.accumulation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @GetMapping("/points/{id}")
    public ResponseEntity<Integer> getDiscountPoints(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int discountPoints = this.clientService.getDiscountPoints(id);

        return new ResponseEntity<>(discountPoints, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody ClientDto clientDto) {
        if (clientDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Client client = this.clientMapper.DTOToEntity(clientDto);
        this.clientService.addClient(client);

        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Client client = this.clientService.getById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<Client> clients = this.clientService.getAll();

        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.clientMapper.entitiesToDTO(clients), HttpStatus.OK);
    }

    @GetMapping("/cardnumber/{card_number}")
    public ResponseEntity<ClientDto> getByCardNumber(@PathVariable("card_number") String cardNumber) {
        if (cardNumber == null) {
            return null;
        }

        Client client = this.clientService.getByCardNumber(cardNumber);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> subtractDiscountPoints(@PathVariable("id") Long id, @RequestParam("points") int pointsToSubtract) {
        if (clientService.getDiscountPoints(id) < pointsToSubtract) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }

        clientService.subtractDiscountPoints(id, pointsToSubtract);
        ClientDto clientDto = clientMapper.entityToDTO(clientService.getById(id));
        return new ResponseEntity<>(clientDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Client client = this.clientService.getById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.clientService.deleteClient(id);

        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.OK);
    }
}
