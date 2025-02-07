package com.alness.health.employee.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.employee.dto.request.EmployeeRequest;
import com.alness.health.employee.dto.response.EmployeeResponse;

public interface EmployeeService {
    public List<EmployeeResponse> find(Map<String, String> parameters);
    public EmployeeResponse findOne(String id);
    public EmployeeResponse save(EmployeeRequest request);
    public EmployeeResponse update(String id, EmployeeRequest request);
    public ResponseDto delete(String id);
}
