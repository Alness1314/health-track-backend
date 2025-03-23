package com.alness.health.modules.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.modules.entity.ModuleEntity;

public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID>,JpaSpecificationExecutor<ModuleEntity>{
    
}
