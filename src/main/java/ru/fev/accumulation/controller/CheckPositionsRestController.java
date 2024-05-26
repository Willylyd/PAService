package ru.fev.accumulation.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.controller.validator.Validator;
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

    @Autowired
    private Validator validator;

    @GetMapping("/check/{checkId}")
    public ResponseEntity<List<CheckPositionDto>> getAllByCheckId(@PathVariable("checkId") Long checkId) {
        if (!validator.isCheckPositionIdValid(checkPositionsService, checkId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CheckPosition> checkPositions = this.checkPositionsService.getAllByCheckId(checkId);
        if (checkPositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CheckPositionDto> addCheckPosition(@RequestBody @Valid CheckPositionDto checkPositionDto,
                                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (isCheckExist(checkPositionDto.getCheckId())) {
            CheckPosition checkPosition = this.checkPositionMapper.DTOToEntity(checkPositionDto);
            this.checkPositionsService.addCheckPosition(checkPosition);

            return new ResponseEntity<>(this.checkPositionMapper.entityToDTO(checkPosition), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<CheckPositionDto>> getAll() {
        List<CheckPosition> checkPositions = this.checkPositionsService.getAll();

        if (checkPositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckPositionDto> deleteCheckPosition(@PathVariable("id") Long id) {
        if (!validator.isCheckPositionIdValid(checkPositionsService, id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        checkPositionsService.deleteCheckPosition(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private boolean isCheckExist(Long id) {
        return checkService.getById(id) != null;
    }
}
