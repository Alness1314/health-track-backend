package com.alness.health.taxpayer.dto.request;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.company.dto.request.CompanyRequest;

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
public class TaxpayerRequest {
    private String rfc;
    private String typePerson;
    private String corporateReasonOrNaturalPerson;
    private LegalRepresentativeRequest legalRepresentative;
    private CompanyRequest companyRequest;
    private AddressRequest address;
}
