package com.alness.health.subsidiary.controller;

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
import com.alness.health.subsidiary.dto.request.SubsidiaryRequest;
import com.alness.health.subsidiary.dto.response.SubsidiaryResponse;
import com.alness.health.subsidiary.service.SubsidiaryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/subsidiary")
@Tag(name = "Subsidiary", description = ".")
public class SubsidiaryController {
    @Autowired
    private SubsidiaryService subsidiaryService;
    

    @GetMapping
    public ResponseEntity<List<SubsidiaryResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<SubsidiaryResponse> response = subsidiaryService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubsidiaryResponse> findOne(@PathVariable String id) {
        SubsidiaryResponse response = subsidiaryService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<SubsidiaryResponse> save(@Valid @RequestBody SubsidiaryRequest request) {
        SubsidiaryResponse response = subsidiaryService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubsidiaryResponse> update(@PathVariable String id, @RequestBody SubsidiaryRequest request) {
        SubsidiaryResponse response = subsidiaryService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = subsidiaryService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
