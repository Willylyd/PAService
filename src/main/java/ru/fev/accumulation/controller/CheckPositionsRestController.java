package ru.fev.accumulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fev.accumulation.dto.CheckPositionToDTO;
import ru.fev.accumulation.dto.DTOtoCheckPosition;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.mapper.CheckPositionMapper;
import ru.fev.accumulation.service.CheckPositionsService;

import java.util.List;

@RestController
@RequestMapping("/checkposition")
public class CheckPositionsRestController {

    @Autowired
    private CheckPositionsService checkPositionsService;

    @Autowired
    private CheckPositionMapper checkPositionMapper;

    @GetMapping("/check/{checkId}")
    public ResponseEntity<List<CheckPositionToDTO>> getAllByCheckId(@PathVariable("checkId") Long checkId) {
        if (checkId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CheckPosition> checkPositions = this.checkPositionsService.getAllByCheckId(checkId);
        if (checkPositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CheckPositionToDTO> addCheckPosition(@RequestBody DTOtoCheckPosition dtOtoCheckPosition) {
        if (dtOtoCheckPosition == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CheckPosition checkPosition = this.checkPositionMapper.DTOToEntity(dtOtoCheckPosition);
        this.checkPositionsService.addCheckPosition(checkPosition);

        return new ResponseEntity<>(this.checkPositionMapper.entityToDTO(checkPosition), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CheckPositionToDTO>> getAll() {
        List<CheckPosition> checkPositions = this.checkPositionsService.getAll();

        if (checkPositions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(this.checkPositionMapper.entitiesToDTO(checkPositions), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CheckPositionToDTO> deleteCheckPosition(@PathVariable("id") Long id) {
        if(id == null) {
            return null;
        }

        CheckPosition checkPosition = this.checkPositionsService.getById(id);
        if(checkPosition == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.checkPositionsService.deleteCheckPosition(id);

        return new ResponseEntity<>(this.checkPositionMapper.entityToDTO(checkPosition), HttpStatus.OK);
    }
}
