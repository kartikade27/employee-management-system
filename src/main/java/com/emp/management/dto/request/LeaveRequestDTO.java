package com.emp.management.dto.request;

import java.time.LocalDate;

import com.emp.management.entities.LeaveType;

import jakarta.validation.constraints.NotNull;
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

public class LeaveRequestDTO {

    private String employeeId;

    @NotNull(message = "Start date is required !")
    private LocalDate startDate;

    @NotNull(message = "End date is required 1")
    private LocalDate endDate;

    @NotNull(message = "Reason is required !")
    private String reason;

    @NotNull(message = "Leave Type is required !")
    private LeaveType leaveType;
}
