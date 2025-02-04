package com.alness.health.subsidiary.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.config.GenericMapper;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.subsidiary.dto.request.SubsidiaryRequest;
import com.alness.health.subsidiary.dto.response.SubsidiaryResponse;
import com.alness.health.subsidiary.entity.SubsidiaryEntity;
import com.alness.health.subsidiary.repository.SubsidiaryRepository;
import com.alness.health.subsidiary.service.SubsidiaryService;
import com.alness.health.subsidiary.specification.SubsidiarySpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubsidiaryServiceImpl implements SubsidiaryService {
    @Autowired
    private SubsidiaryRepository subsidiaryRepository;

    @Autowired
    private GenericMapper mapper;

    @Override
    public List<SubsidiaryResponse> find(Map<String, String> parameters) {
        return subsidiaryRepository.findAll(filterWithParameters(parameters))
                .stream()
                .map(this::mapperDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubsidiaryResponse findOne(String id) {
        SubsidiaryEntity taxpayer = subsidiaryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "subsidiary not found"));
        return mapperDto(taxpayer);
    }

    @Override
    public SubsidiaryResponse save(SubsidiaryRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public SubsidiaryResponse update(String id, SubsidiaryRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private SubsidiaryResponse mapperDto(SubsidiaryEntity source) {
        return mapper.map(source, SubsidiaryResponse.class);
    }

    private Specification<SubsidiaryEntity> filterWithParameters(Map<String, String> parameters) {
        return new SubsidiarySpecification().getSpecificationByFilters(parameters);
    }
}
