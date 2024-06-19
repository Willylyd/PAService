package ru.fev.accumulation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ru.fev.accumulation.dto.CheckPositionDto;
import ru.fev.accumulation.entity.CheckPosition;
import ru.fev.accumulation.mapper.CheckPositionMapper;
import ru.fev.accumulation.service.CheckPositionsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checkposition")
public class CheckPositionsRestController {

    private final CheckPositionsService checkPositionsService;

    private final CheckPositionMapper checkPositionMapper;

    @Autowired
    public CheckPositionsRestController(CheckPositionMapper checkPositionMapper,
                                        CheckPositionsService checkPositionsService) {
        this.checkPositionMapper = checkPositionMapper;
        this.checkPositionsService = checkPositionsService;
    }

    @GetMapping("{id}")
    public ResponseEntity<CheckPositionDto> getById(@PathVariable("id") Long id) {

        var checkPosition = this.checkPositionsService.getById(id);

        return ResponseEntity.ok(this.checkPositionMapper.entityToDTO(checkPosition));
    }

    @GetMapping("/check/{checkId}")
    public ResponseEntity<List<CheckPositionDto>> getAllByCheckId(@PathVariable("checkId") Long checkId) {

        List<CheckPosition> checkPositions = this.checkPositionsService.getAllByCheckId(checkId);

        return ResponseEntity.ok(this.checkPositionMapper.entitiesToDTO(checkPositions));
    }

    @PostMapping
    public ResponseEntity<CheckPositionDto> addCheckPosition(@RequestBody CheckPositionDto checkPositionDto,
                                                             UriComponentsBuilder uriComponentsBuilder) {

        CheckPosition checkPosition = this.checkPositionMapper.DTOToEntity(checkPositionDto);
        this.checkPositionsService.addCheckPosition(checkPosition);

        return ResponseEntity.created(uriComponentsBuilder.path("/checkposition/{id}")
                        .build(Map.of("id", checkPosition.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.checkPositionMapper.entityToDTO(checkPosition));
    }

    @GetMapping
    public ResponseEntity<List<CheckPositionDto>> getAll() {

        List<CheckPosition> checkPositions = this.checkPositionsService.getAll();

        return ResponseEntity.ok(this.checkPositionMapper.entitiesToDTO(checkPositions));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckPosition(@PathVariable("id") Long id) {

        checkPositionsService.deleteCheckPosition(id);

        return ResponseEntity.noContent().build();
    }
}
