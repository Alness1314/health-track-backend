package com.alness.health.patients.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "medical_record")
@Getter
@Setter
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uniqueidentifier")
    private UUID id;

    @Column(name = "reason_consultation", nullable = false, columnDefinition = "character varying(256)")
    private String reasonConsultation;

    @Column(name = "beginning_evolution_state", nullable = false, columnDefinition = "character varying(1024)")
    private String beginningEvolutionState;

    @Column(name = "symptoms_start_date", nullable = false, columnDefinition = "date")
    private LocalDate symptomsStartDate;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false, unique = true)
    private PatientsEntity patient;

}
