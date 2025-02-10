package com.alness.health.app.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alness.health.app.service.AppConfigService;
import com.alness.health.app.service.JsonFileReaderService;
import com.alness.health.cities.service.CityService;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.country.dto.request.CountryRequest;
import com.alness.health.country.dto.response.CountryResponse;
import com.alness.health.country.service.CountryService;
import com.alness.health.profiles.dto.request.ProfileRequest;
import com.alness.health.profiles.dto.response.ProfileResponse;
import com.alness.health.profiles.service.ProfileService;
import com.alness.health.states.dto.response.StateResponse;
import com.alness.health.states.service.StateService;
import com.alness.health.users.dto.request.UserRequest;
import com.alness.health.users.dto.response.UserResponse;
import com.alness.health.users.service.UserService;

@Service
public class AppConfigServiceImpl implements AppConfigService {
    @Autowired
    private CountryService countryService;

    @Autowired
    private StateService stateService;

    @Autowired
    private CityService citiesService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private UserService userService;

    @Autowired
    private JsonFileReaderService jsonFileReaderService;

    private static final String MEXICO = "México";

    @Override
    public ResponseDto createDefaultCountries() {
        if (countryService.find(Map.of()).isEmpty()) {
            List<CountryRequest> countries = Arrays.asList(new CountryRequest("Canadá", "CA"),
                    new CountryRequest("Estados Unidos", "US"), new CountryRequest(MEXICO, "MX"));
            return countryService.multiSaving(countries);
        } else {
            return new ResponseDto("The countries of North America have already been created", HttpStatus.OK, true,
                    null);
        }

    }

    @Override
    public ResponseDto createDefaultStates() {
        Optional<CountryResponse> countryOpt = countryService.find(Map.of("name", MEXICO))
                .stream()
                .findFirst();

        if (countryOpt.isEmpty() || !stateService.find(Collections.emptyMap()).isEmpty()) {
            return new ResponseDto("The states of Mexico have already been created",
                    HttpStatus.OK, true, null);
        }

        List<String> statesMexico = jsonFileReaderService.readJsonFile("json/states_mexico.json");

        return stateService.multiSaving(countryOpt.get().getId().toString(), statesMexico);
    }

    @Override
    public List<ResponseDto> createDefaultCities() {
        List<ResponseDto> responses = new ArrayList<>();
        responses.add(createCitiesTamaulipas());
        responses.add(createCitiesCDMX());
        return responses;
    }

    private ResponseDto createCitiesTamaulipas() {
        Optional<StateResponse> stateOpt = stateService.find(Map.of("name", "Tamaulipas"))
                .stream()
                .findFirst();

        if (stateOpt.isEmpty() || !citiesService.find(Map.of("state", stateOpt.get().getId().toString())).isEmpty()) {
            return new ResponseDto("The states of Mexico have already been created",
                    HttpStatus.OK, true, null);
        }
        List<String> municipiosTamaulipas = jsonFileReaderService.readJsonFile("json/tamaulipas.json");

        return citiesService.multiSaving(stateOpt.get().getId().toString(), municipiosTamaulipas);
    }

    private ResponseDto createCitiesCDMX() {
        Optional<StateResponse> stateOpt = stateService.find(Map.of("name", "Ciudad de México"))
                .stream()
                .findFirst();

        if (stateOpt.isEmpty() || !citiesService.find(Map.of("state", stateOpt.get().getId().toString())).isEmpty()) {
            return new ResponseDto("The cities of cdmx have already been created",
                    HttpStatus.OK, true, null);
        }

        List<String> municipiosCDMX = jsonFileReaderService.readJsonFile("json/cdmx.json");
        return citiesService.multiSaving(stateOpt.get().getId().toString(), municipiosCDMX);
    }

    @Override
    public ResponseDto createDefaultProfiles() {
        if (profileService.find(Map.of()).isEmpty()) {
            List<ProfileRequest> profiles = Arrays.asList(new ProfileRequest("Master"),
                    new ProfileRequest("Administrador"), new ProfileRequest("Empleado"),
                    new ProfileRequest("Paciente"));
            return profileService.multiSaving(profiles);
        } else {
            return new ResponseDto("The profiles have already been created", HttpStatus.OK, true,
                    null);
        }
    }

    @Override
    public ResponseDto createDefaultUser() {
        Optional<UserResponse> stateOpt = userService.find(Map.of("username", "master-admin@msn.com"))
                .stream()
                .findFirst();
        if (stateOpt.isPresent()) {
            return new ResponseDto("The user already exist",
                    HttpStatus.OK, true, null);
        }

        Optional<ProfileResponse> profile = profileService.find(Map.of("name", "Master"))
                .stream()
                .findFirst();

        if (!profile.isEmpty()) {
            List<String> profiles = Collections.singletonList(profile.get().getId().toString());

            UserRequest user = UserRequest.builder()
                    .username("master-admin@msn.com")
                    .password("12345678#")
                    .profiles(profiles)
                    .build();

            userService.save(user);
        }
        return new ResponseDto("The user created",
                HttpStatus.OK, true, null);

    }

}
