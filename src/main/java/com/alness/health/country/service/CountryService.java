package com.alness.health.country.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.country.dto.request.CountryRequest;
import com.alness.health.country.dto.response.CountryResponse;

public interface CountryService {
    public List<CountryResponse> find(Map<String, String> parameters);
    public CountryResponse findOne(String id);
    public CountryResponse save(CountryRequest request);
    public ResponseDto multiSaving(List<CountryRequest> countryList);
    public CountryResponse update(String id, CountryRequest request);
    public ResponseDto delete(String id);
}
