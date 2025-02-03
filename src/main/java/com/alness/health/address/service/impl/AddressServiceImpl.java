package com.alness.health.address.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.address.entity.AddressEntity;
import com.alness.health.address.repository.AddressRepository;
import com.alness.health.address.service.AddressService;
import com.alness.health.address.specification.AddressSpecification;
import com.alness.health.cities.entity.CityEntity;
import com.alness.health.cities.repository.CityRepository;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.country.entity.CountryEntity;
import com.alness.health.country.repository.CountryRepository;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.states.entity.StateEntity;
import com.alness.health.states.repository.StateRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CityRepository cityRepository;

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
    public List<AddressResponse> find(Map<String, String> parameters) {
        return addressRepository.findAll(filterWithParameters(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
    }

    @Override
    public AddressResponse findOne(String id) {
        AddressEntity address = addressRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Address not found"));
        return mapperDto(address);
    }

    @Override
    public AddressResponse save(AddressRequest request) {
        CountryEntity country = countryRepository.findById(UUID.fromString(request.getCountryId()))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Country not found"));

        StateEntity state = stateRepository.findById(UUID.fromString(request.getStateId()))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "State not found"));

        CityEntity city = cityRepository.findById(UUID.fromString(request.getCityId()))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "City not found"));
        AddressEntity address = mapper.map(request, AddressEntity.class);
        try {
            address.setCountry(country);
            address.setState(state);
            address.setCity(city);
            address = addressRepository.save(address);
        } catch (Exception e) {
            log.error("Error to save address {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save address");
        }
        return mapperDto(address);
    }

    @Override
    public AddressResponse update(String id, AddressRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private AddressResponse mapperDto(AddressEntity source) {
        return mapper.map(source, AddressResponse.class);
    }

    private Specification<AddressEntity> filterWithParameters(Map<String, String> parameters) {
        return new AddressSpecification().getSpecificationByFilters(parameters);
    }

}
