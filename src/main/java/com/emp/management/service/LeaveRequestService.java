package com.emp.management.service;

import java.util.List;

import com.emp.management.dto.request.LeaveRequestDTO;
import com.emp.management.dto.response.LeaveResponse;
import com.emp.management.entities.LeaveStatus;

public interface LeaveRequestService {

    LeaveResponse applyLeave(LeaveRequestDTO requestDTO);

    List<LeaveResponse> getLeavesByEmployee(String employeeId);

    List<LeaveResponse> getLeavesByStatus(LeaveStatus status);

    LeaveResponse approveLeave(String leaveId);

    LeaveResponse rejectLeave(String leaveId);
}
