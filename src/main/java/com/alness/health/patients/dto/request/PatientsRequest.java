package com.alness.health.patients.dto.request;

import java.util.List;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.users.dto.request.UserRequest;

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
public class PatientsRequest {
    private String fullName;
    private String birthayDate;
    private Integer age;
    private String gender;
    private String maritalStatus;
    private String nationality;
    private AddressRequest address;
    private String phone;
    private String email;
    private String occupation;
    private String emergencyContact;
    private String emergencyPhone;
    private String relationship;
    private MedicalRecordRequest medicalRecord;
    private UserRequest user;
    private List<String> subsidiaryId;
}
