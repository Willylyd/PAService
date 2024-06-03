package ru.fev.accumulation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.CheckPositionDto;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.mapper.CheckPositionMapper;
import ru.fev.accumulation.service.CheckPositionsService;
import ru.fev.accumulation.service.CheckService;

import java.util.List;

@RestController
@RequestMapping("/checkposition")
public class CheckPositionsRestController {

    @Autowired
    private CheckPositionsService checkPositionsService;

    @Autowired
    private CheckPositionMapper checkPositionMapper;

    @Autowired
    private CheckService checkService;

    @GetMapping("{id}")
    public ResponseEntity<CheckPositionDto> getById(@PathVariable("id") Long id) {

        var checkPosition = this.checkPositionsService.getById(id);

        return new ResponseEntity<>(this.checkPositionMapper.entityToDTO(checkPosition), HttpStatus.OK);
    }

    @GetMapping("/check/{checkId}")
    public ResponseEntity<List<CheckPositionDto>> getAllByCheckId(@PathVariable("checkId") Long checkId) {

        List<CheckPosition> checkPositions = this.checkPositionsService.getAllByCheckId(checkId);

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CheckPositionDto> addCheckPosition(@RequestBody @Valid CheckPositionDto checkPositionDto,
                                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CheckPosition checkPosition = this.checkPositionMapper.DTOToEntity(checkPositionDto);
        this.checkPositionsService.addCheckPosition(checkPosition);

        return new ResponseEntity<>(this.checkPositionMapper.entityToDTO(checkPosition), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CheckPositionDto>> getAll() {

        List<CheckPosition> checkPositions = this.checkPositionsService.getAll();

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckPositionDto> deleteCheckPosition(@PathVariable("id") Long id) {

        checkPositionsService.deleteCheckPosition(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
