package com.alness.health.patients.dto.response;

import java.time.LocalDate;
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
public class MedicalRecordResponse {
    private UUID id;
    private String reasonConsultation;
    private String beginningEvolutionState;
    private LocalDate symptomsStartDate;
}
