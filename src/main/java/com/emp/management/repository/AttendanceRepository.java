package com.emp.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.management.entities.Attendance;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, String> {

    List<Attendance> findByEmployee_EmployeeId(String employeeId);

    List<Attendance> findByDate(LocalDate date);

    Attendance findByEmployee_EmployeeIdAndDate(String employeeId, LocalDate date);

    boolean existsByDate(LocalDate date);


}
