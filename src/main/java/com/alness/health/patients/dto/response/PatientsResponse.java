package com.alness.health.patients.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.subsidiary.dto.response.SubsidiaryResponse;
import com.alness.health.users.dto.response.UserResponse;

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
public class PatientsResponse {
    private UUID id;
    private String fullName;
    private LocalDate birthayDate;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private AddressResponse address;
    private String phone;
    private String email;
    private String occupation;
    private String emergencyContact;
    private String emergencyPhone;
    private String relationship;
    private UserResponse user;
    private MedicalRecordResponse medicalRecord;
    private List<SubsidiaryResponse> subsidiary;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
