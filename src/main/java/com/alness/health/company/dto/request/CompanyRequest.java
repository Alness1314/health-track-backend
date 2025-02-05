package com.alness.health.company.dto.request;


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
public class CompanyRequest {
    private String name;
    private String description;
    private String email;
    private String phone;
    private String imageId;
    private AddressRequest address;
}
