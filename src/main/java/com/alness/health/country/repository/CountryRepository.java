package com.alness.health.country.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.country.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID>, JpaSpecificationExecutor<CountryEntity>{
    
}
