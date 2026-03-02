package com.emp.management.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

    private String employeeId;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String gender;

    private String designation;

    private Double salary;

    private String status;

    private String departmentName;

    private LocalDate joiningDate;

    private LocalDate dateOfBirth;
}
