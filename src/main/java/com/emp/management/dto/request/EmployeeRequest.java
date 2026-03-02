package com.emp.management.dto.request;

import java.time.LocalDate;

import com.emp.management.entities.EmployeeStatus;
import com.emp.management.entities.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class EmployeeRequest {

    @NotBlank(message = "First name is required !")
    private String firstName;

    @NotBlank(message = "Last name is required !")
    private String lastName;

    @Email(message = "Email should be valid !")
    @NotBlank(message = "Email is required !")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Gender is required !")
    private Gender gender;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;

    @Positive(message = "Salary must be positive")
    private Double salary;

    private EmployeeStatus status;

    @NotBlank(message = "Designation is required")
    private String designation;

    private String userId;

    private String departmentId;

}
