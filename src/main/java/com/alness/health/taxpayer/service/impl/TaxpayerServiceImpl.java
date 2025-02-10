package com.alness.health.taxpayer.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.entity.AddressEntity;
import com.alness.health.address.repository.AddressRepository;
import com.alness.health.address.service.AddressService;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.company.dto.response.CompanyResponse;
import com.alness.health.company.entity.CompanyEntity;
import com.alness.health.company.repository.CompanyRepository;
import com.alness.health.company.service.CompanyService;
import com.alness.health.config.GenericMapper;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.taxpayer.dto.request.TaxpayerRequest;
import com.alness.health.taxpayer.dto.response.TaxpayerResponse;
import com.alness.health.taxpayer.entity.LegalRepresentativeEntity;
import com.alness.health.taxpayer.entity.TaxpayerEntity;
import com.alness.health.taxpayer.repository.TaxpayerRepository;
import com.alness.health.taxpayer.service.TaxpayerService;
import com.alness.health.taxpayer.specification.TaxpayerSpecification;
import com.alness.health.utils.TextEncrypterUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaxpayerServiceImpl implements TaxpayerService {
    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private TaxpayerRepository taxpayerRepository;

    @Autowired
    private GenericMapper mapper;

    @Override
    public List<TaxpayerResponse> find(Map<String, String> parameters) {
        return taxpayerRepository.findAll(filterWithParameters(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
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
            // Guardar dirección del contribuyente primero
            AddressResponse addressResponse = addressService.save(request.getAddress());
            AddressEntity addressEntity = addressRepository.findById(addressResponse.getId())
                    .orElseThrow(() -> new RuntimeException("Address not found after save"));
            taxpayer.setAddress(addressEntity);

            // Guardar Legal Representative si aplica
            if (request.getTypePerson().equalsIgnoreCase("Moral") && request.getLegalRepresentative() != null) {
                LegalRepresentativeEntity legalRep = mapper.map(request.getLegalRepresentative(),
                        LegalRepresentativeEntity.class);

                AddressResponse legalRepAddress = addressService.save(request.getLegalRepresentative().getAddress());
                AddressEntity legalRepAddressEntity = addressRepository.findById(legalRepAddress.getId())
                        .orElseThrow(() -> new RuntimeException("Legal Representative Address not found after save"));

                SecretKey key = TextEncrypterUtil.generateKey();
                String keyString = TextEncrypterUtil.keyToString(key);
                legalRep.setFullName(TextEncrypterUtil.encrypt(legalRep.getFullName(), keyString));
                legalRep.setRfc(TextEncrypterUtil.encrypt(legalRep.getRfc(), keyString));
                legalRep.setDataKey(keyString);

                legalRep.setTaxpayer(taxpayer);
                legalRep.setAddress(legalRepAddressEntity);
                taxpayer.setLegalRepresentative(legalRep);
            } else {
                taxpayer.setLegalRepresentative(null);
            }

            // Guardar la empresa antes de asignarla a taxpayer
            CompanyResponse companyResp = companyService.save(request.getCompanyRequest());
            CompanyEntity company = companyRepository.findById(companyResp.getId())
                    .orElseThrow(() -> new RuntimeException("Company not found after save"));
            company.setTaxpayer(taxpayer);
            taxpayer.setCompany(company);

            // Finalmente, guardar taxpayer
            SecretKey key = TextEncrypterUtil.generateKey();
            String keyString = TextEncrypterUtil.keyToString(key);
            taxpayer.setCorporateReasonOrNaturalPerson(
                    TextEncrypterUtil.encrypt(taxpayer.getCorporateReasonOrNaturalPerson(), keyString));
            taxpayer.setRfc(TextEncrypterUtil.encrypt(taxpayer.getRfc(), keyString));
            taxpayer.setDataKey(keyString);

            taxpayer = taxpayerRepository.save(taxpayer);
        } catch (Exception e) {
            log.error("Error to save taxpayer {}", e.getMessage());
            e.printStackTrace();
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
