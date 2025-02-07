package com.alness.health.cities.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.cities.dto.request.CityRequest;
import com.alness.health.cities.dto.response.CityResponse;
import com.alness.health.cities.entity.CityEntity;
import com.alness.health.cities.repository.CityRepository;
import com.alness.health.cities.service.CityService;
import com.alness.health.cities.specification.CitySpecification;
import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.config.GenericMapper;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.states.entity.StateEntity;
import com.alness.health.states.repository.StateRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private GenericMapper mapper;

    @Override
    public List<CityResponse> find(Map<String, String> parameters) {
        return cityRepository.findAll(filterWithParameters(parameters))
                .stream()
                .map(this::mapperDto)
                .toList();
    }

    @Override
    public CityResponse findOne(String id) {
        CityEntity city = cityRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "City not found."));
        return mapperDto(city);
    }

    @Override
    @Transactional
    public CityResponse save(CityRequest request) {
        StateEntity state = stateRepository.findById(UUID.fromString(request.getStateId()))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "State not found"));

        CityEntity newCity = mapper.map(request, CityEntity.class);
        try {
            newCity.setState(state);
            newCity = cityRepository.save(newCity);
        } catch (Exception e) {
            log.error("Error to save city {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save city");
        }
        return mapperDto(newCity);
    }

    @Override
    public CityResponse update(String id, CityRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        CityEntity city = cityRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "City not found."));
        try {
            city.setErased(true);
            cityRepository.save(city);
            return new ResponseDto("The city has been removed.", HttpStatus.ACCEPTED, true, null);
        } catch (Exception e) {
            log.error("Error to delete country ", e);
            return new ResponseDto("An error occurred while deleting the city", HttpStatus.METHOD_NOT_ALLOWED, false,
                    null);
        }
    }

    private CityResponse mapperDto(CityEntity source) {
        return mapper.map(source, CityResponse.class);
    }

    private Specification<CityEntity> filterWithParameters(Map<String, String> parameters) {
        return new CitySpecification().getSpecificationByFilters(parameters);
    }

    @Override
    public ResponseDto multiSaving(String stateId, List<String> citiesList) {
        StateEntity state = stateRepository.findById(UUID.fromString(stateId))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "State not found"));

        List<CityEntity> cities = citiesList.stream()
                .map(req -> new CityEntity(null, req, state, null, null, null))
                .toList();

        try {
            cityRepository.saveAll(cities);
            return new ResponseDto("the cities have been created.", HttpStatus.ACCEPTED, true, null);
        } catch (Exception e) {
            log.error("Error to save all cities ", e);
            return new ResponseDto("An error occurred while creating the cities", HttpStatus.METHOD_NOT_ALLOWED, false,
                    null);
        }
    }

}
