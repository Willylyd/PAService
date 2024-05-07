package ru.fev.accumulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.CheckToDTO;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.dto.DTOtoCheck;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/checks")
public class CheckRestController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private CheckMapper checkMapper;

    @PostMapping
    public ResponseEntity<CheckToDTO> addCheck(@RequestBody DTOtoCheck dtOtoCheck) {
        if (dtOtoCheck == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Check check = this.checkMapper.DTOToEntity(dtOtoCheck);

        this.checkService.addCheck(check);

        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckToDTO> getById(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Check check = this.checkService.getById(id);

        if (check == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckToDTO> deleteCheck(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Check check = this.checkService.getById(id);

        if (check == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.checkService.deleteCheck(id);

        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CheckToDTO>> getByClientId(@PathVariable("clientId") Long clientId) {
        if (clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Check> checks = this.checkService.getByClientId(clientId);

        if (checks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
    }

    @GetMapping("/cardnumber/{cardNumber}")
    public ResponseEntity<List<ClientAndCheckDTO>> getAllByCardNumber(@PathVariable("cardNumber") String cardNumber) {
        if(cardNumber == null) {
            return null;
        }

        List<ClientAndCheckDTO> clientAndCheckDTOs = this.checkService.getAllByCardNumber((cardNumber));
        if(clientAndCheckDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        List<Map<String, Object>> test = this.checkService.getAllByCardNumber(cardNumber);
//
//        test.forEach(System.out::println);

        return new ResponseEntity<>(clientAndCheckDTOs, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CheckToDTO>> getAll() {
        List<Check> checks = this.checkService.getAll();

        if (checks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
    }
}
