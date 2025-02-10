package com.alness.health.app.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.alness.health.app.service.JsonFileReaderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class JsonFileReaderServiceImpl implements JsonFileReaderService{

    private final ObjectMapper objectMapper;

    public JsonFileReaderServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<String> readJsonFile(String fileName) {
        try {
            // Cargar el archivo JSON desde resources
            ClassPathResource resource = new ClassPathResource(fileName);
            return objectMapper.readValue(resource.getInputStream(), new TypeReference<List<String>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }
    
}
