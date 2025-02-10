package com.alness.health.patients.dto.request;

import java.time.LocalDate;

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
public class MedicalRecordRequest {
    private String patientId;
    private String reasonConsultation;
    private String beginningEvolutionState;
    private LocalDate symptomsStartDate;
}
