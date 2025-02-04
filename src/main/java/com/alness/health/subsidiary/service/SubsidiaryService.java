package com.alness.health.subsidiary.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.subsidiary.dto.request.SubsidiaryRequest;
import com.alness.health.subsidiary.dto.response.SubsidiaryResponse;

public interface SubsidiaryService {
    public List<SubsidiaryResponse> find(Map<String, String> parameters);
    public SubsidiaryResponse findOne(String id);
    public SubsidiaryResponse save(SubsidiaryRequest request);
    public SubsidiaryResponse update(String id, SubsidiaryRequest request);
    public ResponseDto delete(String id);
}
