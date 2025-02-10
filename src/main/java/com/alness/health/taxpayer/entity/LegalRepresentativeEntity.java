package com.alness.health.taxpayer.entity;

import java.util.UUID;

import com.alness.health.address.entity.AddressEntity;
import com.alness.health.utils.TextEncrypterUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "legal_representative")
@Getter
@Setter
@NoArgsConstructor
@Slf4j
public class LegalRepresentativeEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, columnDefinition = "character varying(13)")
    private String fullName;

    @Column(nullable = false, columnDefinition = "character varying(13)")
    private String rfc;

    @Column(name = "data_key", nullable = false, columnDefinition = "character varying(64)")
    private String dataKey;

    @OneToOne
    @JoinColumn(name = "taxpayer_id", nullable = false)
    private TaxpayerEntity taxpayer;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @Column(nullable = false, columnDefinition = "boolean")
    private Boolean erased;

    @PrePersist
    private void prePersist() {
        setErased(false);
    }

    @PostLoad
    private void decrypt(){
        if(this.dataKey!=null){
            this.rfc = TextEncrypterUtil.decrypt(rfc, dataKey);
            this.fullName = TextEncrypterUtil.decrypt(fullName, dataKey);
        }
    }

}
