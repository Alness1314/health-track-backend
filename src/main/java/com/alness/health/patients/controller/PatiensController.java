package com.alness.health.patients.controller;

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
import com.alness.health.patients.dto.request.PatientsRequest;
import com.alness.health.patients.dto.response.PatientsResponse;
import com.alness.health.patients.service.PatientsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/patiens")
@Tag(name = "Patiens", description = ".")
public class PatiensController {

    @Autowired
    private PatientsService patientsService;

    @GetMapping()
    public ResponseEntity<List<PatientsResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<PatientsResponse> response = patientsService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientsResponse> findOne(@PathVariable String id) {
        PatientsResponse response = patientsService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<PatientsResponse> save(@Valid @RequestBody PatientsRequest request) {
        PatientsResponse response = patientsService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientsResponse> update(@PathVariable String id, @RequestBody PatientsRequest request) {
        PatientsResponse response = patientsService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = patientsService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
