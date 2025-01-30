package com.alness.health.cities.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.cities.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, UUID>, JpaSpecificationExecutor<CityEntity>{
    
}
