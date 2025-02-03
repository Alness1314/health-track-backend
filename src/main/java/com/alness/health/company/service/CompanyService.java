package com.alness.health.company.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.company.dto.request.CompanyRequest;
import com.alness.health.company.dto.response.CompanyResponse;

public interface CompanyService {
    public List<CompanyResponse> find(Map<String, String> parameters);
    public CompanyResponse findOne(String id);
    public CompanyResponse save(CompanyRequest request);
    public CompanyResponse update(String id, CompanyRequest request);
    public ResponseDto delete(String id);
}
