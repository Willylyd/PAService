package ru.fev.accumulation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;
import ru.fev.accumulation.service.ClientService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checks")
public class CheckRestController {

    @Autowired
    private CheckService checkService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CheckMapper checkMapper;

    @PostMapping
    public ResponseEntity<CheckDto> addCheck(@RequestBody @Valid CheckDto checkDto,
                                             BindingResult bindingResult,
                                             UriComponentsBuilder uriComponentsBuilder) {

        if (bindingResult.hasErrors()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest().build();
        }

        Check check = this.checkMapper.DTOToEntity(checkDto);
        this.checkService.addCheck(check);

//        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.CREATED);
        return ResponseEntity.created(uriComponentsBuilder.path("/checks/{id}")
                        .build(Map.of("id", check.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.checkMapper.entityToDTO(check));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckDto> getById(@PathVariable("id") Long id) {

        Check check = this.checkService.getById(id);

//        return new ResponseEntity<>(this.checkMapper.entityToDTO(check), HttpStatus.OK);
        return ResponseEntity.ok(this.checkMapper.entityToDTO(check));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckDto> deleteCheck(@PathVariable("id") Long id) {

        this.checkService.deleteCheck(id);

//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CheckDto>> getByClientId(@PathVariable("clientId") Long clientId) {

        List<Check> checks = this.checkService.getByClientId(clientId);

        if (checks.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

//        return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
        return ResponseEntity.ok(this.checkMapper.entitiesToDTO(checks));
    }

    @GetMapping("/cardnumber/{cardNumber}")
    public ResponseEntity<List<ClientAndCheckDTO>> getAllByCardNumber(@PathVariable("cardNumber") String cardNumber) {

        List<ClientAndCheckDTO> clientAndCheckDTOs = this.checkService.getAllByCardNumber((cardNumber));

        if (clientAndCheckDTOs.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

//        return new ResponseEntity<>(clientAndCheckDTOs, HttpStatus.OK);
        return ResponseEntity.ok(clientAndCheckDTOs);
    }

    @GetMapping
    public ResponseEntity<List<CheckDto>> getAll() {

        List<Check> checks = this.checkService.getAll();

        if (checks.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build();
        }

//        return new ResponseEntity<>(this.checkMapper.entitiesToDTO(checks), HttpStatus.OK);
        return ResponseEntity.ok(this.checkMapper.entitiesToDTO(checks));
    }
}
