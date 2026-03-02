package com.emp.management.mapper;

import com.emp.management.dto.request.EmployeeRequest;
import com.emp.management.dto.response.EmployeeResponse;
import com.emp.management.entities.Department;
import com.emp.management.entities.Employee;
import com.emp.management.entities.User;

public class EmployeeMapper {

    public static Employee toEntity(EmployeeRequest request, Department department, User user) {
        return Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .joiningDate(request.getJoiningDate())
                .salary(request.getSalary())
                .designation(request.getDesignation())
                .user(user)
                .department(department)
                .status(request.getStatus())
                .build();
    }

    public static EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse employeeResponse = EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .gender(employee.getGender().name())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .status(employee.getStatus().name())
                .joiningDate(employee.getJoiningDate())
                .dateOfBirth(employee.getDateOfBirth())
                .build();

        if (employee.getDepartment() != null) {
            employeeResponse.setDepartmentName(employee.getDepartment().getDepartmentName());
        }

        return employeeResponse;
    }
}
