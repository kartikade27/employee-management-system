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
public class LeaveResponse {

    private String leaveId;

    private String employeeName;

    private LocalDate startDate;

    private LocalDate endDate;

    private String leaveType;

    private String status;
}
