package com.alness.health.cities.controller;

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

import com.alness.health.cities.dto.request.CityRequest;
import com.alness.health.cities.dto.response.CityResponse;
import com.alness.health.cities.service.CityService;
import com.alness.health.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/cities")
@Tag(name = "Cities", description = ".")
public class CityController {
    @Autowired
    private CityService cityService;

    @GetMapping()
    public ResponseEntity<List<CityResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<CityResponse> response = cityService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> findOne(@PathVariable String id) {
        CityResponse response = cityService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<CityResponse> save(@Valid @RequestBody CityRequest request) {
        CityResponse response = cityService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/all/{stateId}")
    public ResponseEntity<ResponseDto> multiSave(@PathVariable String stateId,
            @Valid @RequestBody List<String> request) {
        ResponseDto response = cityService.multiSaving(stateId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityResponse> update(@PathVariable String id, @RequestBody CityRequest request) {
        CityResponse response = cityService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = cityService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
