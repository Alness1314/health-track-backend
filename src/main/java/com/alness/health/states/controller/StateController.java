package com.alness.health.states.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.states.dto.request.StateRequest;
import com.alness.health.states.dto.response.StateResponse;
import com.alness.health.states.service.StateService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/states")
@Tag(name = "States", description = ".")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping
    public ResponseEntity<List<StateResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<StateResponse> response = stateService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateResponse> findOne(@PathVariable String id) {
        StateResponse response = stateService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<StateResponse> save(@Valid @RequestBody StateRequest request) {
        StateResponse response = stateService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/all/{countryId}")
    public ResponseEntity<ResponseDto> multiSave(@PathVariable String countryId, @Valid @RequestBody List<String> request) {
        ResponseDto response = stateService.multiSaving(countryId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StateResponse> update(@PathVariable String id, @RequestBody StateRequest request) {
        StateResponse response = stateService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = stateService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
