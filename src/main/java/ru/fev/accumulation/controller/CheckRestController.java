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
            return ResponseEntity.badRequest().build();
        }

        Check check = this.checkMapper.DTOToEntity(checkDto);
        this.checkService.addCheck(check);

        return ResponseEntity.created(uriComponentsBuilder.path("/checks/{id}")
                        .build(Map.of("id", check.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.checkMapper.entityToDTO(check));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CheckDto> getById(@PathVariable("id") Long id) {

        Check check = this.checkService.getById(id);

        return ResponseEntity.ok(this.checkMapper.entityToDTO(check));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckDto> deleteCheck(@PathVariable("id") Long id) {

        this.checkService.deleteCheck(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<CheckDto>> getByClientId(@PathVariable("clientId") Long clientId) {

        List<Check> checks = this.checkService.getAllByClientId(clientId);

        if (checks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.checkMapper.entitiesToDTO(checks));
    }

    @GetMapping("/cardnumber/{cardNumber}")
    public ResponseEntity<List<ClientAndCheckDTO>> getAllByCardNumber(@PathVariable("cardNumber") String cardNumber) {

        List<ClientAndCheckDTO> clientAndCheckDTOs = this.checkService.getAllByCardNumber((cardNumber));

        if (clientAndCheckDTOs.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(clientAndCheckDTOs);
    }

    @GetMapping
    public ResponseEntity<List<CheckDto>> getAll() {

        List<Check> checks = this.checkService.getAll();

        if (checks.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.checkMapper.entitiesToDTO(checks));
    }
}
