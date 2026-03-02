package com.emp.management.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;

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
public class AttendanceResponse {

    private String attendanceId;

    private String employeeName;

    private LocalDate date;

    private LocalTime checkIn;

    private LocalTime checkOut;

    private String status;
}
