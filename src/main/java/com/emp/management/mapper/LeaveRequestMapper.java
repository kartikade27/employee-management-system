package com.emp.management.mapper;

import com.emp.management.dto.request.LeaveRequestDTO;
import com.emp.management.dto.response.LeaveResponse;
import com.emp.management.entities.Employee;
import com.emp.management.entities.LeaveRequest;

public class LeaveRequestMapper {

    public static LeaveRequest toEntity(LeaveRequestDTO requestDTO, Employee employee) {

        return LeaveRequest.builder()
                .startDate(requestDTO.getStartDate())
                .endDate(requestDTO.getEndDate())
                .reason(requestDTO.getReason())
                .leaveType(requestDTO.getLeaveType())
                .employee(employee)
                .build();
    }

    public static LeaveResponse toResponse(LeaveRequest leaveRequest) {
        return LeaveResponse.builder()
                .leaveId(leaveRequest.getLeaveId())
                .startDate(leaveRequest.getStartDate())
                .endDate(leaveRequest.getEndDate())
                .leaveType(leaveRequest.getLeaveType().name())
                .status(leaveRequest.getStatus().name())
                .employeeName(
                        leaveRequest.getEmployee().getFirstName() + " " + leaveRequest.getEmployee().getLastName())
                .build();

    }
}
