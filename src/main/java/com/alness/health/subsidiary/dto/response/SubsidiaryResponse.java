package com.alness.health.subsidiary.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.taxpayer.dto.response.TaxpayerResponse;

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
public class SubsidiaryResponse {
    private UUID id;
    private String nickname;
    private String phone;
    private String email;
    private String responsible;
    private String openingHours;
    private TaxpayerResponse taxpayer;
    private AddressResponse address;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
