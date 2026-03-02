package com.emp.management.mapper;

import com.emp.management.dto.request.DepartmentRequest;
import com.emp.management.dto.response.DepartmentResponse;
import com.emp.management.entities.Department;

public class DepartmentMapper {

    public static Department toEntity(DepartmentRequest departmentRequest) {
        return Department.builder()
                .departmentName(departmentRequest.getDepartmentName())
                .description(departmentRequest.getDescription())
                .build();
    }

    public static DepartmentResponse toResponse(Department department) {
        return DepartmentResponse.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .description(department.getDescription())
                .build();
    }
}
