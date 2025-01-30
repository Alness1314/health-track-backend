package com.alness.health.states.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.country.entity.CountryEntity;
import com.alness.health.country.repository.CountryRepository;
import com.alness.health.exceptions.RestExceptionHandler;
import com.alness.health.states.dto.request.StateRequest;
import com.alness.health.states.dto.response.StateResponse;
import com.alness.health.states.entity.StateEntity;
import com.alness.health.states.repository.StateRepository;
import com.alness.health.states.service.StateService;
import com.alness.health.states.specification.StateSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StateServiceImpl implements StateService {
    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    private ModelMapper mapper = new ModelMapper();

    @Override
    public List<StateResponse> find(String countryId, Map<String, String> parameters) {
        return stateRepository.findAll(filterWithParameters(updateParams(countryId, parameters)))
                .stream()
                .map(this::mapperDto)
                .collect(Collectors.toList());
    }

    @Override
    public StateResponse findOne(String countryId, String id) {
        StateEntity state = stateRepository.findOne(filterWithParameters(Map.of("id", id, "country", countryId)))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "State not found"));
        return mapperDto(state);
    }

    @Override
    public StateResponse save(String countryId, StateRequest request) {
        CountryEntity country = countryRepository.findById(UUID.fromString(countryId))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "Country not found"));

        StateEntity newState = mapper.map(request, StateEntity.class);
        try {
            newState.setCountry(country);
            newState = stateRepository.save(newState);
        } catch (Exception e) {
            log.error("Error to save state {}", e.getMessage());
            throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error to save state");
        }
        return mapperDto(newState);
    }

    @Override
    public StateResponse update(String countryId, String id, StateRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String countryId, String id) {
        StateEntity state = stateRepository.findOne(filterWithParameters(Map.of("id", id, "country", countryId)))
                .orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
                        "State not found"));
        try {
            state.setErased(true);
            stateRepository.save(state);
            return new ResponseDto("The state has been removed.", HttpStatus.ACCEPTED, true, null);
        } catch (Exception e) {
            log.error("Error to delete country ", e);
            return new ResponseDto("An error occurred while deleting the state", HttpStatus.METHOD_NOT_ALLOWED, false,
                    null);
        }
    }

    private Map<String, String> updateParams(String countryId, Map<String, String> params) {
        params.put("country", countryId);
        return params;
    }

    private StateResponse mapperDto(StateEntity source) {
        return mapper.map(source, StateResponse.class);
    }

    private Specification<StateEntity> filterWithParameters(Map<String, String> parameters) {
        return new StateSpecification().getSpecificationByFilters(parameters);
    }

}
