package com.alness.health.profiles.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.common.keys.Filters;
import com.alness.health.config.GenericMapper;
import com.alness.health.modules.dto.response.ModuleResponse;
import com.alness.health.modules.entity.ModuleEntity;
import com.alness.health.profiles.dto.request.ProfileRequest;
import com.alness.health.profiles.dto.response.ProfileResponse;
import com.alness.health.profiles.entity.ProfileEntity;
import com.alness.health.profiles.repository.ProfileRepository;
import com.alness.health.profiles.service.ProfileService;
import com.alness.health.profiles.specification.ProfileSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService{
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private GenericMapper mapper;
    
    @Override
    public ProfileResponse save(ProfileRequest request) {
        ProfileEntity newProfile = mapper.map(request, ProfileEntity.class);
        try {
            newProfile = profileRepository.save(newProfile);
            return mapperDto(newProfile);
        } catch (Exception e) {
            log.error("Error to save profile ", e);
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @Override
    public ProfileResponse findOne(String id) {
        ProfileEntity profile = profileRepository.findOne(filterWithParameters(Map.of(Filters.KEY_ID, id)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapperDto(profile);
    }

    @Override
    public List<ProfileResponse> find(Map<String, String> params) {
        return profileRepository.findAll(filterWithParameters(params))
                .stream().map(this::mapperDto).toList();
    }

    @Override
    public ProfileResponse update(String id, ProfileRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ResponseDto delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    private ProfileResponse mapperDto(ProfileEntity source) {
        return mapper.map(source, ProfileResponse.class);
    }

    public Specification<ProfileEntity> filterWithParameters(Map<String, String> parameters) {
        return new ProfileSpecification().getSpecificationByFilters(parameters);
    }

    @Override
    public ProfileResponse findByName(String name) {
        ProfileEntity profile = profileRepository.findOne(filterWithParameters(Map.of(Filters.KEY_NAME, name)))
                .orElse(null);
        if (profile == null) {
            return null;
        }
        return mapperDto(profile);
    }

    @Override
    public List<ModuleResponse> getModulesByProfile(String profileId) {
        UUID id = UUID.fromString(profileId);
        log.info("profile id: {}", id);
        ProfileEntity profile = profileRepository.findByIdWithModules(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        Set<ModuleEntity> allowedModules = profile.getModules();

        return profile.getModules().stream()
                .filter(module -> module.getParent() == null) // Inicia desde los módulos raíz
                .map(module -> convertToDto(module, allowedModules))
                .toList();
    }

    private ModuleResponse convertToDto(ModuleEntity module, Set<ModuleEntity> allowedModules) {
        return ModuleResponse.builder()
                .id(module.getId())
                .name(module.getName())
                .route(module.getRoute())
                .iconName(module.getIconName())
                .isParent(module.getIsParent())
                .erased(module.getErased())
                .children(module.getChildren().stream()
                        .filter(allowedModules::contains)
                        .map(child -> convertToDto(child, allowedModules)).toList())
                .build();
    }

    @Override
    public ResponseDto multiSaving(List<ProfileRequest> profileList) {
       try {
			List<ProfileEntity> profiles = profileList.stream()
					.map(req -> new ProfileEntity(null, req.getName(), null, null, null, null))
					.toList();
                    profileRepository.saveAll(profiles);
			return new ResponseDto("the profiles have been created.", HttpStatus.ACCEPTED, true, null);
		} catch (Exception e) {
			log.error("Error to save all profiles ", e);
			return new ResponseDto("An error occurred while creating the profiles", HttpStatus.METHOD_NOT_ALLOWED,
					false,
					null);
		}
    }
}
