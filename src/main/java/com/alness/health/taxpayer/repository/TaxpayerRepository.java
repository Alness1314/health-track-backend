package com.alness.health.taxpayer.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.alness.health.taxpayer.entity.TaxpayerEntity;

public interface TaxpayerRepository extends JpaRepository<TaxpayerEntity, UUID>, JpaSpecificationExecutor<TaxpayerEntity>{
    @Query(value = "select * from taxpayer", nativeQuery = true)
    List<TaxpayerEntity> findAllNative();
}
