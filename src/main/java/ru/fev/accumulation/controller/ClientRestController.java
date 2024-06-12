package ru.fev.accumulation.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fev.accumulation.dto.ClientDto;
import ru.fev.accumulation.entity.Client;
import ru.fev.accumulation.mapper.ClientMapper;
import ru.fev.accumulation.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/clients")
public class ClientRestController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientMapper clientMapper;

    @GetMapping("/points/{id}")
    public ResponseEntity<Integer> getDiscountPoints(@PathVariable("id") Long id) {

        int discountPoints = this.clientService.getDiscountPoints(id);

        return ResponseEntity.ok(discountPoints);
    }

    @PostMapping
    public ResponseEntity<ClientDto> addClient(@RequestBody @Valid ClientDto clientDto,
                                               BindingResult bindingResult,
                                               UriComponentsBuilder uriComponentsBuilder) {

        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }

        Client client = this.clientMapper.DTOToEntity(clientDto);
        this.clientService.addClient(client);

//        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.CREATED);
        return ResponseEntity.created(uriComponentsBuilder.path("/clients/{id}")
                        .build(Map.of("id", client.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.clientMapper.entityToDTO(client));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") Long id) {

        Client client = this.clientService.getById(id);

//        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.OK);
        return ResponseEntity.ok(this.clientMapper.entityToDTO(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {

        List<Client> clients = this.clientService.getAll();

        if (clients.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

//        return new ResponseEntity<>(this.clientMapper.entitiesToDTO(clients), HttpStatus.OK);
        return ResponseEntity.ok(this.clientMapper.entitiesToDTO(clients));
    }

    @GetMapping("/cardnumber/{card_number}")
    public ResponseEntity<ClientDto> getByCardNumber(@PathVariable("card_number") String cardNumber) {

        Client client = this.clientService.getByCardNumber(cardNumber);
//        return new ResponseEntity<>(this.clientMapper.entityToDTO(client), HttpStatus.OK);
        return ResponseEntity.ok(this.clientMapper.entityToDTO(client));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDto> subtractDiscountPoints(@PathVariable("id") Long id, @RequestParam("points") int pointsToSubtract) {

        clientService.subtractDiscountPoints(id, pointsToSubtract);
        ClientDto clientDto = clientMapper.entityToDTO(clientService.getById(id));
//        return new ResponseEntity<>(clientDto, HttpStatus.OK);
        return ResponseEntity.ok(clientDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDto> deleteClient(@PathVariable("id") Long id) {

        clientService.deleteClient(id);

//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }
}
