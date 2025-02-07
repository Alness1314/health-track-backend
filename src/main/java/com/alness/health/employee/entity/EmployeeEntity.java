package com.alness.health.employee.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.users.entity.UserEntity;
import com.alness.health.files.entity.FileEntity;
import com.alness.health.subsidiary.entity.SubsidiaryEntity;

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

@Getter
@Setter
@Entity
@Table(name = "employee")
public class EmployeeEntity {
     @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(nullable = false, columnDefinition = "character varying(128)")
    private String names;

    @Column(name = "last_name",nullable = false, columnDefinition = "character varying(64)")
    private String lastName;

    @Column(name = "mother_last_name", nullable = true, columnDefinition = "character varying(64)")
    private String motherLastName;

    @Column(name = "birthay_date", nullable = false, columnDefinition = "date")
    private LocalDate birthayDate;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String gender;

    @Column(name = "identification_number", nullable = false, columnDefinition = "character varying(256)")
    private String identificationNumber;

    @Column(nullable = false, columnDefinition = "character varying(256)")
    private String rfc;

    @Column(nullable = false, columnDefinition = "character varying(64)")
    private String nationality;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "image_id", unique = true, nullable = true)
    private FileEntity image;

    @Column(name = "create_at", nullable = false, columnDefinition = "timestamp without time zone")
    private LocalDateTime createAt;

    @Column(name = "update_at", nullable = false, updatable = true, columnDefinition = "timestamp without time zone")
    private LocalDateTime updateAt;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;


    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "employee_subsidiary", 
    joinColumns = @JoinColumn(name = "id_user", nullable = false), 
    inverseJoinColumns = @JoinColumn(name = "id_subsidiary", nullable = false))
	private List<SubsidiaryEntity> subsidiary = new ArrayList<>();

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
