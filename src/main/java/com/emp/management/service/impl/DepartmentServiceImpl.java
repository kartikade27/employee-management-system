package com.emp.management.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.DepartmentRequest;
import com.emp.management.dto.response.DepartmentResponse;
import com.emp.management.entities.Department;
import com.emp.management.exceptions.ResourceAlreadyExistsExceptions;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.DepartmentMapper;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.service.DepartmentService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    // Only ADMIN can create department
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (this.departmentRepository.existsByDepartmentName(request.getDepartmentName())) {
            throw new ResourceAlreadyExistsExceptions("Department already exists ");
        }
        Department entity = DepartmentMapper.toEntity(request);
        Department departmentSaved = this.departmentRepository.save(entity);
        return DepartmentMapper.toResponse(departmentSaved);
    }

    // ADMIN & HR both can view department by ID
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DepartmentResponse getDepartmentById(String departmentId) {
        Department department = this.departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));
        return DepartmentMapper.toResponse(department);
    }

    // ADMIN & HR both can view all departments
    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<DepartmentResponse> getAllDepartments() {
        return this.departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ADMIN & HR both can search department
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<DepartmentResponse> searchDepartmentByName(String name) {
        List<Department> search = this.departmentRepository.findByDepartmentNameContainingIgnoreCase(name);
        if (search.isEmpty()) {
            throw new ResourceNotFoundExceptions("Department not found !");
        }
        return search.stream().map(DepartmentMapper::toResponse).collect(Collectors.toList());
    }

    // Only ADMIN can update department
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public DepartmentResponse updateDepartment(String departmentId, DepartmentRequest departmentRequest) {
        Department department = this.departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));

        Optional.ofNullable(departmentRequest.getDepartmentName()).ifPresent(department::setDepartmentName);
        Optional.ofNullable(departmentRequest.getDescription()).ifPresent(department::setDescription);

        Department updateDepartment = this.departmentRepository.save(department);
        return DepartmentMapper.toResponse(updateDepartment);
    }

    // Only ADMIN can delete department
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CustomApiResponse deleteDepartment(String departmentId) {
        Department department = this.departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));
        this.departmentRepository.delete(department);
        return CustomApiResponse.builder().message("Department successfully deleted !").build();
    }
}
