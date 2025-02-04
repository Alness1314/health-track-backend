package com.alness.health.taxpayer.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.entity.AddressEntity;
import com.alness.health.address.service.AddressService;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.company.dto.response.CompanyResponse;
import com.alness.health.company.entity.CompanyEntity;
import com.alness.health.company.service.CompanyService;
import com.alness.health.config.GenericMapper;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.taxpayer.dto.request.TaxpayerRequest;
import com.alness.health.taxpayer.dto.response.TaxpayerResponse;
import com.alness.health.taxpayer.entity.LegalRepresentativeEntity;
import com.alness.health.taxpayer.entity.TaxpayerEntity;
import com.alness.health.taxpayer.repository.LegalRepresentativeRepository;
import com.alness.health.taxpayer.repository.TaxpayerRepository;
import com.alness.health.taxpayer.service.TaxpayerService;
import com.alness.health.taxpayer.specification.TaxpayerSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaxpayerServiceImpl implements TaxpayerService {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TaxpayerRepository taxpayerRepository;

    @Autowired
    private LegalRepresentativeRepository legalRepresentativeRepository;

    @Autowired
    private GenericMapper mapper;

    @Override
    public List<TaxpayerResponse> find(Map<String, String> parameters) {
        return taxpayerRepository.findAll(filterWithParameters(parameters))
                .stream()
                .map(this::mapperDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaxpayerResponse findOne(String id) {
        TaxpayerEntity taxpayer = taxpayerRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "taxpayer not found"));
        return mapperDto(taxpayer);
    }

    @Override
    public TaxpayerResponse save(TaxpayerRequest request) {
        TaxpayerEntity taxpayer = mapper.map(request, TaxpayerEntity.class);
        try {
            if (request.getLegalRepresentative() != null) {
                LegalRepresentativeEntity legaRep = mapper.map(request.getLegalRepresentative(),
                        LegalRepresentativeEntity.class);
                AddressResponse legalRepAddress = addressService.save(request.getLegalRepresentative().getAddress());
                legaRep.setAddress(mapper.map(legalRepAddress, AddressEntity.class));
                legaRep = legalRepresentativeRepository.save(legaRep);
                taxpayer.setLegalRepresentative(legaRep);
            }

        } catch (Exception e) {
            log.error("Error to save legal representative {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save legal representative");
        }
        CompanyResponse companyResp = companyService.save(request.getCompanyRequest());
        CompanyEntity company = mapper.map(companyResp, CompanyEntity.class);
        taxpayer.setCompany(company);

        try {
            AddressResponse address = addressService.save(request.getAddress());
            taxpayer.setAddress(mapper.map(address, AddressEntity.class));
            taxpayer = taxpayerRepository.save(taxpayer);
        } catch (Exception e) {
            log.error("Error to save taxpayer {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save taxpayer");
        }
        return mapperDto(taxpayer);
    }

    @Override
    public TaxpayerResponse update(String id, TaxpayerRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private TaxpayerResponse mapperDto(TaxpayerEntity source) {
        return mapper.map(source, TaxpayerResponse.class);
    }

    private Specification<TaxpayerEntity> filterWithParameters(Map<String, String> parameters) {
        return new TaxpayerSpecification().getSpecificationByFilters(parameters);
    }

    
}
