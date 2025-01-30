package com.alness.health.states.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.states.dto.request.StateRequest;
import com.alness.health.states.dto.response.StateResponse;

public interface StateService {
    public List<StateResponse> find(String countryId, Map<String, String> parameters);
    public StateResponse findOne(String countryId, String id);
    public StateResponse save(String countryId, StateRequest request);
    public StateResponse update(String countryId, String id, StateRequest request);
    public ResponseDto delete(String countryId, String id);
}
