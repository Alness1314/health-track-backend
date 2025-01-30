package com.alness.health.states.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.country.entity.CountryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "states")
public class StateEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id", columnDefinition = "uuid", nullable = false)
    private CountryEntity country;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamp without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

    @PrePersist
    private void prePersist(){
        setCreateAt(LocalDateTime.now());
        setUpdateAt(LocalDateTime.now());
        setErased(false);
    }

    @PreUpdate
    private void preUpdate(){
        setUpdateAt(LocalDateTime.now());
    }
}
