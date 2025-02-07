package com.alness.health.modules.service;

import java.util.List;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.modules.dto.request.ModuleRequest;
import com.alness.health.modules.dto.response.ModuleResponse;

public interface ModuleService {
    public ModuleResponse createModule(ModuleRequest module);
    public ModuleResponse updateModule(String id, ModuleRequest moduleDetails);
    public ResponseDto deleteModule(String id);
    public ModuleResponse getModuleById(String id);
    public List<ModuleResponse> getAllModules();
    public ModuleResponse assignChildToParent(String parentId, String childId);
}
