package com.alness.health.subsidiary.dto.request;

import com.alness.health.address.dto.request.AddressRequest;

import jakarta.validation.constraints.NotNull;
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
public class SubsidiaryRequest {
    private String nickname;
    private String phone;
    private String email;
    private String responsible;
    private String openingHours;
    
    @NotNull
    private String taxpayerId;
    
    @NotNull
    private AddressRequest address;
}
