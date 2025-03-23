package com.alness.health.app.service;

import java.util.List;

import com.alness.health.app.dto.ResponseServer;
import com.alness.health.common.dto.ResponseDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AppConfigService {
    public ResponseDto createDefaultCountries();
    public ResponseDto createDefaultStates();
    public List<ResponseDto> createDefaultCities();
    public ResponseDto createDefaultProfiles();
    public ResponseDto createDefaultUser();
    public ResponseServer checkStatusSession(HttpServletRequest request);
}
