package com.alness.health.company.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.files.entity.FileEntity;
import com.alness.health.taxpayer.entity.TaxpayerEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
public class CompanyEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "name", nullable = false, columnDefinition = "character varying(128)")
    private String name;

    @Column(name = "description", nullable = false, columnDefinition = "character varying(256)")
    private String description;

    @Column(name = "email", nullable = false, columnDefinition = "character varying(32)")
    private String email;

    @Column(name = "phone", nullable = false, columnDefinition = "character varying(20)")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private FileEntity image;

    @OneToOne(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private TaxpayerEntity taxpayer;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestampsdasdasdasdasd without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

    @PrePersist
    private void prePersist() {
        setCreateAt(LocalDateTime.now());
        setUpdateAt(LocalDateTime.now());
        setErased(false);
    }

    @PreUpdate
    private void preUpdate() {
        setUpdateAt(LocalDateTime.now());
    }

}
