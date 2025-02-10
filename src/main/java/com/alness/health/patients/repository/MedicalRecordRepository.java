package com.alness.health.patients.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.patients.entity.MedicalRecordEntity;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecordEntity, UUID>, JpaSpecificationExecutor<MedicalRecordEntity>{
    
}
