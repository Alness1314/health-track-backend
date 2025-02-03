package com.alness.health.taxpayer.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.company.dto.response.CompanyResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaxpayerResponse {
    private UUID id;
    private String rfc;
    private String typePerson;
    private String corporateReasonOrNaturalPerson;
    private LegalRepresentativeResponse legalRepresentative;
    private AddressResponse address;
    private CompanyResponse company;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
