package com.alness.health.employee.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alness.health.address.service.AddressService;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.config.GenericMapper;
import com.alness.health.employee.dto.request.EmployeeRequest;
import com.alness.health.employee.dto.response.EmployeeResponse;
import com.alness.health.employee.entity.EmployeeEntity;
import com.alness.health.employee.repository.EmployeeRepository;
import com.alness.health.employee.service.EmployeeService;
import com.alness.health.employee.specification.EmployeeSpecification;
import com.alness.health.files.service.FileService;
import com.alness.health.users.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Autowired
    GenericMapper mapper;

    @Override
    public List<EmployeeResponse> find(Map<String, String> parameters) {
        return employeeRepository.findAll(filterWithParams(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
    }

    @Override
    public EmployeeResponse findOne(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public EmployeeResponse save(EmployeeRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public EmployeeResponse update(String id, EmployeeRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private Specification<EmployeeEntity> filterWithParams(Map<String, String> parameters) {
        return new EmployeeSpecification().getSpecificationByFilters(parameters);
    }

    private EmployeeResponse mapperDto(EmployeeEntity source) {
        return mapper.map(source, EmployeeResponse.class);
    }
}
