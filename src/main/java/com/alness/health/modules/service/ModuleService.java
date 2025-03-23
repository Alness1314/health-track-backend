package com.alness.health.modules.service;

import java.util.List;
import java.util.Map;

import com.alness.health.app.dto.ResponseServer;
import com.alness.health.common.dto.ResponseDto;
import com.alness.health.modules.dto.ModuleDto;
import com.alness.health.modules.dto.request.ModuleRequest;
import com.alness.health.modules.dto.response.ModuleResponse;

public interface ModuleService {
    public ResponseServer multiSave(List<ModuleRequest> module);
    public ModuleResponse createModule(ModuleRequest module);
    public ModuleResponse updateModule(String id, ModuleRequest moduleDetails);
    public ResponseDto deleteModule(String id);
    public ModuleResponse getModuleById(String id);
    public List<ModuleResponse> getAllModules(Map<String, String> params);
    public ModuleResponse assignChildToParent(String parentId, String childId);
    public List<ModuleDto> find(Map<String, String> params);
}
