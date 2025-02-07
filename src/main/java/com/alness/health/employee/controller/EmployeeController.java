package com.alness.health.employee.controller;

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
import com.alness.health.employee.dto.request.EmployeeRequest;
import com.alness.health.employee.dto.response.EmployeeResponse;
import com.alness.health.employee.service.EmployeeService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("${api.prefix}/employees")
@Tag(name = "Employees", description = ".")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    @GetMapping()
    public ResponseEntity<List<EmployeeResponse>> findAll(@RequestParam Map<String, String> parameters) {
        List<EmployeeResponse> response = employeeService.find(parameters);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> findOne(@PathVariable String id) {
        EmployeeResponse response = employeeService.findOne(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PostMapping()
    public ResponseEntity<EmployeeResponse> save(@Valid @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable String id, @RequestBody EmployeeRequest request) {
        EmployeeResponse response = employeeService.update(id, request);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> delete(@PathVariable String id) {
        ResponseDto response = employeeService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }
}
