package com.emp.management.dto.request;

import java.time.LocalDate;
import java.time.LocalTime;

import com.emp.management.entities.AttendanceStatus;

import jakarta.validation.constraints.NotBlank;
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
public class AttendanceRequest {

    @NotBlank(message = "employee is required !")
    private String employeeId;

    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private AttendanceStatus status;
}
