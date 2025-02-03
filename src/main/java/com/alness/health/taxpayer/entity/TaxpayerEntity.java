package com.alness.health.taxpayer.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.company.entity.CompanyEntity;

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
@Table(name = "taxpayer")
@Getter
@Setter
public class TaxpayerEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(name = "corporate_reason_or_natural_person", nullable = false, columnDefinition = "character varying(64)")
    private String corporateReasonOrNaturalPerson;

    @Column(nullable = false, columnDefinition = "character varying(13)")
    private String rfc;

    @Column(name = "type_person", nullable = false, columnDefinition = "character varying(12)")
    private String typePerson;

    @OneToOne
    @JoinColumn(name = "legal_representative_id", nullable = true)
    private LegalRepresentativeEntity legalRepresentative;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @OneToOne
    @JoinColumn(name = "company_id", nullable = true)
    private CompanyEntity company;

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
