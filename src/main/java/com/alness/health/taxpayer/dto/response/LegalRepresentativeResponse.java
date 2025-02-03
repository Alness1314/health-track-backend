package com.alness.health.taxpayer.dto.response;

import java.util.UUID;

import com.alness.health.address.dto.response.AddressResponse;

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
public class LegalRepresentativeResponse {
    private UUID id;
    private String fullName;
    private String rfc;
    private AddressResponse address;
    private Boolean erased;
}
