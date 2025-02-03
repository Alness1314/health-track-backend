package com.alness.health.company.dto.response;

import java.time.LocalDateTime;
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
public class CompanyResponse {
    private UUID id;
    private String name;
    private String description;
    private String email;
    private String phone;
    private AddressResponse address;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
