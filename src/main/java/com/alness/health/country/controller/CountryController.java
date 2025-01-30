package com.alness.health.country.controller;

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
import com.alness.health.country.dto.request.CountryRequest;
import com.alness.health.country.dto.response.CountryResponse;
import com.alness.health.country.service.CountryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/countrys")
@Tag(name = "Countries", description = ".")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping()
    public ResponseEntity<List<CountryResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<CountryResponse> response = countryService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryResponse> findOne(@PathVariable String id) {
        CountryResponse response = countryService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<CountryResponse> save(@Valid @RequestBody CountryRequest request) {
        CountryResponse response = countryService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryResponse> update(@PathVariable String id, @RequestBody CountryRequest request) {
        CountryResponse response = countryService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = countryService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
