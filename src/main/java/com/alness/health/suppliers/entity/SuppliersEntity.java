package com.alness.health.suppliers.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class SuppliersEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String name;

    @Column(name = "contact_name", columnDefinition = "character varying(128)")
    private String contactName;

    @Column(columnDefinition = "character varying(15)")
    private String phone;

    @Column(columnDefinition = "character varying(64)")
    private String email;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamp without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;
}
