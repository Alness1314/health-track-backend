package com.alness.health.country.dto.response;

import java.time.LocalDateTime;
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
public class CountryResponse {
    private UUID id;
    private String name;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
