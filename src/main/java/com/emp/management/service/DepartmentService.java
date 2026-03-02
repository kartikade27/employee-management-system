package com.emp.management.service;

import java.util.List;

import com.emp.management.dto.request.DepartmentRequest;
import com.emp.management.dto.response.DepartmentResponse;
import com.emp.management.utils.CustomApiResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(String departmentId);

    List<DepartmentResponse> getAllDepartments();

    List<DepartmentResponse> searchDepartmentByName(String name);

    DepartmentResponse updateDepartment(String departmentId, DepartmentRequest departmentRequest);

    CustomApiResponse deleteDepartment(String departmentId);
}
