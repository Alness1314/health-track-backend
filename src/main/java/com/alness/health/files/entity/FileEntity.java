package com.alness.health.files.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, columnDefinition = "character varying(256)")
    private String name;

    @Column(name = "extension", nullable = false, columnDefinition = "character varying(64)")
    private String extension;

    @Column(name = "mime_type", nullable = false, columnDefinition = "character varying(128)")
    private String mimeType;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamps without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

}
