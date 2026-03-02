package com.emp.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.management.entities.Employee;
import com.emp.management.entities.EmployeeStatus;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByEmail(String email);

    List<Employee> findByDepartment_DepartmentId(String departmentId);

    List<Employee> findByStatus(EmployeeStatus status);

    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);

    List<Employee> findByDesignationContainingIgnoreCase(String designation);

    boolean existsByEmail(String email);

}
