package com.alness.health.taxpayer.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.taxpayer.entity.LegalRepresentativeEntity;

public interface LegalRepresentativeRepository extends JpaRepository<LegalRepresentativeEntity, UUID>, JpaSpecificationExecutor<LegalRepresentativeEntity>{
    
}
