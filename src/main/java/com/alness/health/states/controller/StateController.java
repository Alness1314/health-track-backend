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
@RequestMapping("${api.prefix}/country")
@Tag(name = "States", description = ".")
public class StateController {
    @Autowired
    private StateService stateService;

    @GetMapping("/{countryId}/states")
    public ResponseEntity<List<StateResponse>> findAll(@PathVariable String countryId,
            @RequestParam Map<String, String> parameters) {
        List<StateResponse> response = stateService.find(countryId, parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{countryId}/states/{id}")
    public ResponseEntity<StateResponse> findOne(@PathVariable String countryId, @PathVariable String id) {
        StateResponse response = stateService.findOne(countryId, id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("/{countryId}/states")
    public ResponseEntity<StateResponse> save(@PathVariable String countryId,
            @Valid @RequestBody StateRequest request) {
        StateResponse response = stateService.save(countryId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{countryId}/states/{id}")
    public ResponseEntity<StateResponse> update(@PathVariable String countryId, @PathVariable String id,
            @RequestBody StateRequest request) {
        StateResponse response = stateService.update(countryId, id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{countryId}/states/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String countryId, @PathVariable String id) {
        ResponseDto response = stateService.delete(countryId, id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
