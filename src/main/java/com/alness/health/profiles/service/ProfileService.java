package com.alness.health.profiles.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.modules.dto.response.ModuleResponse;
import com.alness.health.profiles.dto.request.ProfileRequest;
import com.alness.health.profiles.dto.response.ProfileResponse;

public interface ProfileService {
    public ProfileResponse save(ProfileRequest request);
    public ProfileResponse findOne(String id);
    public ProfileResponse findByName(String name);
    public List<ProfileResponse> find(Map<String, String> params);
    public ProfileResponse update(String id, ProfileRequest request);
    public ResponseDto delete(String id);
    public List<ModuleResponse> getModulesByProfile(String profileId);
    public ResponseDto multiSaving(List<ProfileRequest> profileList);
}
