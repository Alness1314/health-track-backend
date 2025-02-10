package com.alness.health.patients.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.subsidiary.entity.SubsidiaryEntity;
import com.alness.health.users.entity.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "patients")
@Getter
@Setter
public class PatientsEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "full_name", nullable = false, columnDefinition = "character varying(128)")
    private String fullName;

    @Column(name = "birthay_date", nullable = false, columnDefinition = "date")
    private LocalDate birthayDate;

    @Column(name = "curp", nullable = true, columnDefinition = "character varying(256)")
    private LocalDate curp;

    @Column(name = "rfc", nullable = true, columnDefinition = "character varying(256)")
    private LocalDate rfc;

    @Column(nullable = false, columnDefinition = "integter")
    private Integer age;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String gender;

    @Column(name = "marital_status", nullable = true, columnDefinition = "character varying(64)")
    private String maritalStatus;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String phone;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String email;

    @Column(nullable = true, columnDefinition = "character varying(64)")
    private String occupation;

    @Column(name = "emergency_contact", nullable = false, columnDefinition = "character varying(64)")
    private String emergencyContact;

    @Column(name = "emergency_phone", nullable = false, columnDefinition = "character varying(64)")
    private String emergencyPhone;

    @Column(name = "relationship", nullable = true, columnDefinition = "character varying(64)")
    private String relationship;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamp without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalRecordEntity medicalRecord;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "patients_subsidiary", joinColumns = @JoinColumn(name = "id_patients", nullable = false), inverseJoinColumns = @JoinColumn(name = "id_subsidiary", nullable = false))
    private List<SubsidiaryEntity> subsidiary = new ArrayList<>();

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
