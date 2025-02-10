package com.alness.health.patients.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.patients.entity.PatientsEntity;

public interface PatientsRepository extends JpaRepository<PatientsEntity, UUID>, JpaSpecificationExecutor<PatientsEntity>{
    
}
