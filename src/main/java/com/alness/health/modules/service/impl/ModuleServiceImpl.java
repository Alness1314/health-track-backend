package com.alness.health.modules.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.config.GenericMapper;
import com.alness.health.modules.dto.request.ModuleRequest;
import com.alness.health.modules.dto.response.ModuleResponse;
import com.alness.health.modules.entity.ModuleEntity;
import com.alness.health.modules.repository.ModuleRepository;
import com.alness.health.modules.service.ModuleService;
import com.alness.health.profiles.entity.ProfileEntity;
import com.alness.health.profiles.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    GenericMapper mapper;

    @Override
    public ModuleResponse createModule(ModuleRequest module) {
        ModuleEntity newModule = mapper.map(module, ModuleEntity.class);
        try {
            for (String profileId : module.getProfile()) {
                ProfileEntity profile = profileRepository.findById(UUID.fromString(profileId)).orElse(null);
                log.info("profile: {}", profile);

                if (profile != null) {
                    newModule.getProfiles().add(profile);
                    profile.getModules().add(newModule); // Actualiza el otro lado de la relación
                }
            }
            log.info("module to save {}", newModule);
            newModule = moduleRepository.save(newModule);
            return mapperModule(newModule);
        } catch (Exception e) {
            log.error("error to save", e);
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "error to save module");
        }

    }

    @Override
    public ModuleResponse updateModule(String id, ModuleRequest moduleDetails) {
        throw new UnsupportedOperationException("Unimplemented method 'updateModule'");
    }

    @Override
    public ResponseDto deleteModule(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteModule'");
    }

    @Override
    public ModuleResponse getModuleById(String id) {
        ModuleEntity module = moduleRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "module not found"));
        return mapperModule(module);
    }

    @Override
    public List<ModuleResponse> getAllModules() {
        return moduleRepository.findAll().stream()
                .filter(module -> module.getParent() == null) // Excluir los módulos asignados a un padre
                .map(this::convertToDto).toList();
    }

    @Override
    public ModuleResponse assignChildToParent(String parentId, String childId) {
        ModuleEntity parent = moduleRepository.findById(UUID.fromString(parentId))
                .orElseThrow(() -> new IllegalArgumentException("Parent module not found with id: " + parentId));
        ModuleEntity child = moduleRepository.findById(UUID.fromString(childId))
                .orElseThrow(() -> new IllegalArgumentException("Child module not found with id: " + childId));

        if (Boolean.FALSE.equals(parent.getIsParent())) {
            throw new IllegalArgumentException("Module with id: " + parentId + " is not marked as a parent");
        }

        child.setParent(parent);
        parent.getChildren().add(child);

        moduleRepository.save(child);
        return convertToDto(moduleRepository.save(parent));
    }

    public ModuleResponse mapperModule(ModuleEntity module) {
        return mapper.map(module, ModuleResponse.class);
    }

    private ModuleResponse convertToDto(ModuleEntity module) {
        return ModuleResponse.builder()
                .id(module.getId())
                .name(module.getName())
                .route(module.getRoute())
                .iconName(module.getIconName())
                .isParent(module.getIsParent())
                .erased(module.getErased())
                .children(module.getChildren().stream().map(this::convertToDto).toList())
                .build();
    }

}
