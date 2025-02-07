package com.alness.health.employee.dto.request;

import com.alness.health.address.dto.request.AddressRequest;
import com.alness.health.users.dto.request.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequest {
    private String names;
    private String lastName;
    private String motherLastName;
    private LocalDate birthayDate;
    private String gender;
    private String identificationNumber;
    private String rfc;
    private String nationality;
    private AddressRequest address;
    private UserRequest user;
    private String imageId;
}
