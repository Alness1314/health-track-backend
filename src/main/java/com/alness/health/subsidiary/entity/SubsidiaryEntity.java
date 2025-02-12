package com.alness.health.subsidiary.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.taxpayer.entity.TaxpayerEntity;
import com.alness.health.employee.entity.EmployeeEntity;
import com.alness.health.inventory.entity.InventoryEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subsidiary")
@Getter
@Setter
public class SubsidiaryEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, columnDefinition = "character varying(128)")
    private String nickname;

    @Column(nullable = true, columnDefinition = "character varying(20)")
    private String phone;

    @Column(nullable = true, columnDefinition = "character varying(64)")
    private String email;

    @Column(nullable = true, columnDefinition = "character varying(64)")
    private String responsible;

    @Column(name = "opening_hours", nullable = true, columnDefinition = "character varying(128)")
    private String openingHours;

    @ManyToOne
    @JoinColumn(name = "taxpayer_id", nullable = false)
    private TaxpayerEntity taxpayer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToMany(mappedBy = "subsidiary")
    private List<EmployeeEntity> employee = new ArrayList<>();

     @OneToMany(mappedBy = "subsidiary", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryEntity> inventory;

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
