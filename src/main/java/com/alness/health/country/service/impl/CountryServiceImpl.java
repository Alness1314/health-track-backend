package com.alness.health.country.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.common.ApiCodes;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.config.GenericMapper;
import com.alness.health.country.dto.request.CountryRequest;
import com.alness.health.country.dto.response.CountryResponse;
import com.alness.health.country.entity.CountryEntity;
import com.alness.health.country.repository.CountryRepository;
import com.alness.health.country.service.CountryService;
import com.alness.health.country.specification.CountrySpecification;
import com.alness.health.exceptions.RestExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {
	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private GenericMapper mapper;

	@Override
	public List<CountryResponse> find(Map<String, String> parameters) {
		return countryRepository.findAll(filterWithParameters(parameters))
				.stream()
				.map(this::mapperDto)
				.toList();
	}

	@Override
	public CountryResponse findOne(String id) {
		CountryEntity country = countryRepository.findOne(filterWithParameters(Map.of("id", id)))
				.orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
						"Country not found"));
		return mapperDto(country);
	}

	@Override
	public CountryResponse save(CountryRequest request) {
		CountryEntity newCountry = mapper.map(request, CountryEntity.class);
		try {
			newCountry = countryRepository.save(newCountry);
		} catch (Exception e) {
			log.error("Error to save country {}", e.getMessage());
			throw new RestExceptionHandler(ApiCodes.API_CODE_500, HttpStatus.INTERNAL_SERVER_ERROR,
					"Error to save country");
		}
		return mapperDto(newCountry);
	}

	@Override
	public CountryResponse update(String id, CountryRequest request) {
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public ResponseDto delete(String id) {
		CountryEntity country = countryRepository.findOne(filterWithParameters(Map.of("id", id)))
				.orElseThrow(() -> new RestExceptionHandler(ApiCodes.API_CODE_404, HttpStatus.NOT_FOUND,
						"Country not found"));
		try {
			country.setErased(true);
			countryRepository.save(country);
			return new ResponseDto("The country has been removed.", HttpStatus.ACCEPTED, true, null);
		} catch (Exception e) {
			log.error("Error to delete country ", e);
			return new ResponseDto("An error occurred while deleting the country", HttpStatus.METHOD_NOT_ALLOWED, false,
					null);
		}
	}

	private CountryResponse mapperDto(CountryEntity source) {
		return mapper.map(source, CountryResponse.class);
	}

	private Specification<CountryEntity> filterWithParameters(Map<String, String> parameters) {
		return new CountrySpecification().getSpecificationByFilters(parameters);
	}

	@Override
	public ResponseDto multiSaving(List<CountryRequest> countryList) {
		try {
			List<CountryEntity> countries = countryList.stream()
					.map(req -> new CountryEntity(null, req.getName(), req.getCode(), null, null, null))
					.toList();
			countryRepository.saveAll(countries);
			return new ResponseDto("the countries have been created.", HttpStatus.ACCEPTED, true, null);
		} catch (Exception e) {
			log.error("Error to save all states ", e);
			return new ResponseDto("An error occurred while creating the countries", HttpStatus.METHOD_NOT_ALLOWED,
					false,
					null);
		}
	}

}
