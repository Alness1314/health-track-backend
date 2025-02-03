package com.alness.health.taxpayer.dto.request;

import com.alness.health.address.dto.request.AddressRequest;

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
public class LegalRepresentativeRequest {
    private String fullName;
    private String rfc;
    private AddressRequest address;
}
