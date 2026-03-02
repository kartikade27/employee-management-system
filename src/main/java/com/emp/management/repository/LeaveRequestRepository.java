package com.emp.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.management.entities.LeaveRequest;
import com.emp.management.entities.LeaveStatus;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, String> {

    List<LeaveRequest> findByEmployee_EmployeeId(String employeeId);

    List<LeaveRequest> findByStatus(LeaveStatus status);

    List<LeaveRequest> findByEmployee_EmployeeIdAndStatus(String employeeId, LeaveStatus status);
}
