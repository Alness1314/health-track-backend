package com.alness.health.taxpayer.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.taxpayer.dto.request.TaxpayerRequest;
import com.alness.health.taxpayer.dto.response.TaxpayerResponse;

public interface TaxpayerService {
    public List<TaxpayerResponse> find(Map<String, String> parameters);
    public TaxpayerResponse findOne(String id);
    public TaxpayerResponse save(TaxpayerRequest request);
    public TaxpayerResponse update(String id, TaxpayerRequest request);
    public ResponseDto delete(String id);
}
