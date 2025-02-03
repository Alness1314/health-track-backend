package com.alness.health.states.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.states.dto.request.StateRequest;
import com.alness.health.states.dto.response.StateResponse;

public interface StateService {
    public List<StateResponse> find(Map<String, String> parameters);
    public StateResponse findOne(String id);
    public StateResponse save(StateRequest request);
    public ResponseDto multiSaving(String countryId, List<String> stateList);
    public StateResponse update(String id, StateRequest request);
    public ResponseDto delete(String id);
}
