package com.alness.health.files.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alness.health.files.dto.FileResponse;
import com.alness.health.files.service.FileService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("${api.prefix}/files")
@Tag(name = "Files", description = ".")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        FileResponse response = fileService.storeFile(file);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> findAll() {
        List<FileResponse> files = fileService.find();
        return new ResponseEntity<>(files, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getDownloadFile(@PathVariable String id) {
        return fileService.downloadFileAsResource(id);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        return fileService.loadFileAsResource(id);
    }
}
