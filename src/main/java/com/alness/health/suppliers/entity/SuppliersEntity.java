package com.alness.health.suppliers.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.users.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class SuppliersEntity {
     @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    private String name;
    private String contactName;
    private String phone;
    private String email;
    private AddressEntity address;
    private UserEntity user;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestampsdasdasdasdasd without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;
}
