package com.emp.management.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.AttendanceRequest;
import com.emp.management.dto.response.AttendanceResponse;
import com.emp.management.entities.Attendance;
import com.emp.management.entities.AttendanceStatus;
import com.emp.management.entities.Employee;
import com.emp.management.exceptions.ResourceAlreadyExistsExceptions;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.AttendanceMapper;
import com.emp.management.repository.AttendanceRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.service.AttendanceService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final EmployeeRepository employeeRepository;

    private final AttendanceRepository attendanceRepository;

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public AttendanceResponse markAttendance(AttendanceRequest request) {
        Employee employee = this.employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

        if (this.attendanceRepository.existsByDate(request.getDate())) {
            throw new ResourceAlreadyExistsExceptions("You have already marked your attendance.");
        }

        Attendance entity = AttendanceMapper.toEntity(request, employee);
        entity.setStatus(AttendanceStatus.PRESENT);
        entity.setEmployee(employee);
        Attendance attendanceSaved = this.attendanceRepository.save(entity);
        return AttendanceMapper.toResponse(attendanceSaved);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public List<AttendanceResponse> getAttendanceByEmployee(String employeeId) {
        return this.attendanceRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(AttendanceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {
        return this.attendanceRepository.findByDate(date)
                .stream()
                .map(AttendanceMapper::toResponse)
                .collect(
                        Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public AttendanceResponse getAttendanceEmployeeAndDate(String employeeId, LocalDate date) {

        Attendance attendance = this.attendanceRepository
                .findByEmployee_EmployeeIdAndDate(employeeId, date);

        if (attendance == null) {
            throw new ResourceNotFoundExceptions(
                    "Attendance not found for employeeId: " + employeeId + " and date: " + date);
        }

        return AttendanceMapper.toResponse(attendance);
    }

    @Override
    @PreAuthorize("hasRole('EMPLOYEE')")
    public CustomApiResponse checkOutTime(String employeeId) {
        List<Attendance> attendanceEmployee = this.attendanceRepository.findByEmployee_EmployeeId(employeeId);
        attendanceEmployee.stream().forEach(emp -> {
            emp.setCheckOut(LocalTime.now());
            this.attendanceRepository.save(emp);
        });
        return CustomApiResponse.builder().message("Employee check-out today").build();
    }

}
