package ru.fev.accumulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.CheckToDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;

import java.util.List;

@RestController
@RequestMapping("/checks")
public class CheckRestController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private CheckMapper checkMapper;

    @PostMapping
    public ResponseEntity<CheckToDTO> addCheck(@RequestBody Check check) {
        if (check == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.checkService.addCheck(check);

        return new ResponseEntity<>(checkMapper.entityToDTO(check), HttpStatus.CREATED);
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

        return new ResponseEntity<>(checkMapper.entityToDTO(check), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Check> deleteCheck(@PathVariable("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Check check = this.checkService.getById(id);

        if (check == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.checkService.deleteCheck(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clientid/{clientId}")
    public ResponseEntity<List<CheckToDTO>> getByCardNumber(@PathVariable("clientId") Long clientId) {
        if (clientId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Check> checks = this.checkService.getByClientId(clientId);

        if (checks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(checkMapper.entitiesToDTO(checks), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CheckToDTO>> getAll() {
        List<Check> checks = this.checkService.getAll();

        if (checks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(checkMapper.entitiesToDTO(checks), HttpStatus.OK);
    }
}
