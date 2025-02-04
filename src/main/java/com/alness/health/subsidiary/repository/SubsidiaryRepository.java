package com.alness.health.subsidiary.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.subsidiary.entity.SubsidiaryEntity;

public interface SubsidiaryRepository extends JpaRepository<SubsidiaryEntity, UUID>, JpaSpecificationExecutor<SubsidiaryEntity>{
    
}
