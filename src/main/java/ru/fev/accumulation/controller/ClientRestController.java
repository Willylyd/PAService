package ru.fev.accumulation.controller;

import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.ClientDTO;
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
    public ResponseEntity<ClientDTO> addClient(@RequestBody Client client) {
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.clientService.addClient(client);
        return new ResponseEntity<>(clientMapper.entityToDTO(client), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Client client = this.clientService.getById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientMapper.entityToDTO(client), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<Client> clients = this.clientService.getAll();

        if (clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clientMapper.entitiesToDTO(clients), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> deleteClient(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Client client = this.clientService.getById(id);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.clientService.deleteClient(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
