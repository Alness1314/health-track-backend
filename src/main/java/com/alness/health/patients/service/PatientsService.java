package com.alness.health.patients.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.patients.dto.request.PatientsRequest;
import com.alness.health.patients.dto.response.PatientsResponse;

public interface PatientsService {
    public List<PatientsResponse> find(Map<String, String> parameters);
    public PatientsResponse findOne(String id);
    public PatientsResponse save(PatientsRequest request);
    public PatientsResponse update(String id, PatientsRequest request);
    public ResponseDto delete(String id);
}
