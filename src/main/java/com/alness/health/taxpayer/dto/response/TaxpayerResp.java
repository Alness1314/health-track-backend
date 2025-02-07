package com.alness.health.taxpayer.dto.response;

import java.util.UUID;

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
public class TaxpayerResp {
    private UUID id;
    private String rfc;
    private String typePerson;
    private String corporateReasonOrNaturalPerson;
    private LegalRepResp legalRepresentative;
}
