package com.alness.health.address.controller;

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

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.service.AddressService;
import com.alness.health.common.dto.ResponseDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/address")
@Tag(name = "Address", description = ".")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<List<AddressResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<AddressResponse> response = addressService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> findOne(@PathVariable String id) {
        AddressResponse response = addressService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<AddressResponse> save(@Valid @RequestBody AddressRequest request) {
        AddressResponse response = addressService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> update(@PathVariable String id, @RequestBody AddressRequest request) {
        AddressResponse response = addressService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = addressService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

}
