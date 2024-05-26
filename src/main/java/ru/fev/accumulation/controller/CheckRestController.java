package ru.fev.accumulation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.controller.validator.Validator;
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;
import ru.fev.accumulation.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/checks")
public class CheckRestController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CheckMapper checkMapper;

    @Autowired
    private Validator validator;

    @PostMapping
    public ResponseEntity<CheckDto> addCheck(@RequestBody @Valid CheckDto checkDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Check check = this.checkMapper.DTOToEntity(checkDto);
        this.checkService.addCheck(check);

        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckDto> getById(@PathVariable("id") Long id) {
        if (validator.isCheckIdValid(checkService, id)) {
            Check check = this.checkService.getById(id);
            return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckDto> deleteCheck(@PathVariable("id") Long id) {
        if (validator.isCheckIdValid(checkService, id)) {
            this.checkService.deleteCheck(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CheckDto>> getByClientId(@PathVariable("clientId") Long clientId) {
        if (validator.isClientIdValid(clientService, clientId)) {
            List<Check> checks = this.checkService.getByClientId(clientId);

            if (checks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/cardnumber/{cardNumber}")
    public ResponseEntity<List<ClientAndCheckDTO>> getAllByCardNumber(@PathVariable("cardNumber") String cardNumber) {
        if (validator.isCardNumberValid(clientService, cardNumber)) {
            List<ClientAndCheckDTO> clientAndCheckDTOs = this.checkService.getAllByCardNumber((cardNumber));
            if (clientAndCheckDTOs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(clientAndCheckDTOs, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<CheckDto>> getAll() {
        List<Check> checks = this.checkService.getAll();

        if (checks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
    }
}
