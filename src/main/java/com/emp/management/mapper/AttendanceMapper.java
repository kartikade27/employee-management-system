package com.emp.management.mapper;

import com.emp.management.dto.request.AttendanceRequest;
import com.emp.management.dto.response.AttendanceResponse;
import com.emp.management.entities.Attendance;
import com.emp.management.entities.Employee;

public class AttendanceMapper {

    public static Attendance toEntity(AttendanceRequest request, Employee employee) {
        return Attendance.builder()
                .date(request.getDate())
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .status(request.getStatus())
                .employee(employee)
                .build();
    }

    public static AttendanceResponse toResponse(Attendance attendance) {
        return AttendanceResponse.builder()
                .attendanceId(attendance.getAttendanceId())
                .date(attendance.getDate())
                .checkIn(attendance.getCheckIn())
                .checkOut(attendance.getCheckOut())
                .status(attendance.getStatus().name())
                .employeeName(
                        attendance.getEmployee().getFirstName() + " " + attendance.getEmployee().getLastName())
                .build();
    }
    
}
