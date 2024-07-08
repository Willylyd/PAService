package ru.fev.accumulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fev.accumulation.dto.CheckDto;
import ru.fev.accumulation.dto.ClientAndCheckDTO;
import ru.fev.accumulation.entity.Check;
import ru.fev.accumulation.mapper.CheckMapper;
import ru.fev.accumulation.service.CheckService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/checks")
public class CheckRestController {

    private final CheckService checkService;

    private final CheckMapper checkMapper;

    @Autowired
    public CheckRestController(CheckService checkService,
                               CheckMapper checkMapper) {
        this.checkMapper = checkMapper;
        this.checkService = checkService;
    }

    @PostMapping
    public ResponseEntity<CheckDto> addCheck(@RequestBody CheckDto checkDto,
                                             BindingResult bindingResult,
                                             UriComponentsBuilder uriComponentsBuilder) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Check check = this.checkMapper.DTOToEntity(checkDto);
        this.checkService.addCheck(check);

        return ResponseEntity.ok(this.checkMapper.entityToDTO(check));
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
    public ResponseEntity<List<CheckDto>> getAllByClientId(@PathVariable("clientId") Long clientId) {

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
