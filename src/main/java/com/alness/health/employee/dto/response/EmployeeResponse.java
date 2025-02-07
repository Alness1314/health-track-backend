package com.alness.health.employee.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import com.alness.health.address.dto.response.AddressResponse;
import com.alness.health.users.dto.response.UserResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.alness.health.files.dto.FileResponse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {
    private UUID id;
    private String names;
    private String lastName;
    private String motherLastName;
    private LocalDate birthayDate;
    private String gender;
    private String identificationNumber;
    private String rfc;
    private String nationality;
    private AddressResponse address;
    private UserResponse user;
    private FileResponse image;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private Boolean erased;
}
