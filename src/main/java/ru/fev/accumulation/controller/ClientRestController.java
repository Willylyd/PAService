package ru.fev.accumulation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import ru.fev.accumulation.dto.DiscountPointsDTO;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping("{card_number}")
    public ResponseEntity<Integer> getDiscountPoints(@PathVariable("card_number") String cardNumber) {
        if(cardNumber.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int discountPoints = this.clientService.getDiscountPoints(cardNumber);

        return new ResponseEntity<>(discountPoints, HttpStatus.OK);
    }

    public ResponseEntity<Client> addClient(Client client) {
        if(client == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.clientService.addClient(client);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = this.clientService.getAll();

        if(clients.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(clients, HttpStatus.OK);
    }

    public ResponseEntity<Client> updateDiscountPoints(DiscountPointsDTO dto) {
        //TODO update logic

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
