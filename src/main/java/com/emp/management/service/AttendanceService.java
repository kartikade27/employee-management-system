package com.emp.management.service;

import java.time.LocalDate;
import java.util.List;

import com.emp.management.dto.request.AttendanceRequest;
import com.emp.management.dto.response.AttendanceResponse;
import com.emp.management.utils.CustomApiResponse;

public interface AttendanceService {

    AttendanceResponse markAttendance(AttendanceRequest request);

    List<AttendanceResponse> getAttendanceByEmployee(String employeeId);

    List<AttendanceResponse> getAttendanceByDate(LocalDate date);

    AttendanceResponse getAttendanceEmployeeAndDate(String employeeId, LocalDate date);

    CustomApiResponse checkOutTime(String employeeId);

    
}
