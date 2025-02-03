package com.alness.health.address.dto.request;

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
public class AddressRequest {
    private String nickname;
    private String street;
    private String number;
    private String suburb;
    private String zipCode;
    private String reference;
    private String countryId;
    private String stateId;
    private String cityId;
}
