package com.alness.health.company.service.impl;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.entity.AddressEntity;
import com.alness.health.address.service.AddressService;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.company.dto.request.CompanyRequest;
import com.alness.health.company.dto.response.CompanyResponse;
import com.alness.health.company.entity.CompanyEntity;
import com.alness.health.company.repository.CompanyRepository;
import com.alness.health.company.service.CompanyService;
import com.alness.health.exceptions.RestExceptionHandler;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressService addressService;

    ModelMapper mapper = new ModelMapper();

    @PostConstruct
    private void init() {
        configureModelMapper();
    }

    private void configureModelMapper() {
        mapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<CompanyResponse> find(Map<String, String> parameters) {
        throw new UnsupportedOperationException("Unimplemented method 'find'");
    }

    @Override
    public CompanyResponse findOne(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findOne'");
    }

    @Override
    public CompanyResponse save(CompanyRequest request) {
        CompanyEntity company = mapper.map(request, CompanyEntity.class);
        AddressResponse address = addressService.save(request.getAddress());
        company.setAddress(mapper.map(address, AddressEntity.class));
        try {
            company = companyRepository.save(company);
        } catch (Exception e) {
            log.error("Error to save company {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save company");
        }
        return mapperDto(company);
    }

    @Override
    public CompanyResponse update(String id, CompanyRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private CompanyResponse mapperDto(CompanyEntity source) {
        return mapper.map(source, CompanyResponse.class);
    }
}
