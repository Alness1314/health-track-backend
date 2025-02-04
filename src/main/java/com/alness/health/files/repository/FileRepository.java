package com.alness.health.files.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.alness.health.files.entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, UUID>, JpaSpecificationExecutor<FileEntity>{
    
}
