package com.alness.health.address.service;

import java.util.List;
import java.util.Map;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.common.dto.ResponseDto;

public interface AddressService {
    public List<AddressResponse> find(Map<String, String> parameters);
    public AddressResponse findOne(String id);
    public AddressResponse save(AddressRequest request);
    public AddressResponse update(String id, AddressRequest request);
    public ResponseDto delete(String id);
}
