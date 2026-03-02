package com.emp.management.service;

import java.util.List;

import com.emp.management.dto.request.EmployeeRequest;
import com.emp.management.dto.response.EmployeeResponse;
import com.emp.management.entities.EmployeeStatus;
import com.emp.management.utils.CustomApiResponse;

public interface EmployeeService {

    EmployeeResponse createEmployee(EmployeeRequest request);

    EmployeeResponse getEmployeeById(String employeeId);

    List<EmployeeResponse> getAllEmployees();

    List<EmployeeResponse> getEmployeeByStatus(EmployeeStatus status);

    List<EmployeeResponse> searchEmployeeByFirstName(String firstName);

    List<EmployeeResponse>findByDepartmentsByEmployee(String departmentId);

    List<EmployeeResponse> searchEmployeeByDesignation(String designation);

    EmployeeResponse updateEmployee(String employeeId, EmployeeRequest employeeRequest);

    CustomApiResponse updateEmployeeStatus(String employeeId, EmployeeStatus status);

    CustomApiResponse deleteEmployee(String employeeId);
}
