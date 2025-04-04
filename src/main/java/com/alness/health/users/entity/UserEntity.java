package com.alness.health.users.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.alness.health.profiles.entity.ProfileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
public class UserEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, columnDefinition = "character varying(64)")
    private String username;

    @Column(nullable = false, columnDefinition = "character varying(256)")
    private String password;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean verified;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"), uniqueConstraints = {
    @UniqueConstraint(columnNames = { "user_id", "profile_id" }) })
    private List<ProfileEntity> profiles;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

    @Column(nullable = false, updatable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime created;

    @Column(nullable = false, updatable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime updated;

    @PrePersist
    public void prePersist(){
        setVerified(false);
        setErased(false);
        setCreated(LocalDateTime.now());
        setUpdated(LocalDateTime.now());
    }
}
