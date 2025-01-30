package com.alness.health.cities.service;

import java.util.List;
import java.util.Map;

import com.alness.health.cities.dto.request.CityRequest;
import com.alness.health.cities.dto.response.CityResponse;
import com.alness.health.common.dto.ResponseDto;

public interface CityService {
    public List<CityResponse> find(Map<String, String> parameters);
    public CityResponse findOne(String id);
    public CityResponse save(CityRequest request);
    public CityResponse update(String id, CityRequest request);
    public ResponseDto delete(String id);
}
