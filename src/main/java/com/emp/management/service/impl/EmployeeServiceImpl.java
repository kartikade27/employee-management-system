package com.emp.management.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.EmployeeRequest;
import com.emp.management.dto.response.EmployeeResponse;
import com.emp.management.entities.Department;
import com.emp.management.entities.Employee;
import com.emp.management.entities.EmployeeStatus;
import com.emp.management.entities.User;
import com.emp.management.exceptions.ResourceAlreadyExistsExceptions;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.EmployeeMapper;
import com.emp.management.repository.DepartmentRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.repository.UserRepository;
import com.emp.management.service.EmployeeService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public EmployeeResponse createEmployee(EmployeeRequest request) {
        if (this.employeeRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsExceptions("Employee already Exists !");
        }

        User user = this.userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found !"));

        Department department = this.departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));

        Employee entity = EmployeeMapper.toEntity(request, department, user);
        entity.setStatus(EmployeeStatus.ACTIVE);
        Employee employee = this.employeeRepository.save(entity);
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public EmployeeResponse getEmployeeById(String employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));
        return EmployeeMapper.toResponse(employee);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<EmployeeResponse> getAllEmployees() {
        return this.employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<EmployeeResponse> getEmployeeByStatus(EmployeeStatus status) {
        return this.employeeRepository.findByStatus(status)
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<EmployeeResponse> searchEmployeeByFirstName(String firstName) {
        return this.employeeRepository.findByFirstNameContainingIgnoreCase(firstName)
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<EmployeeResponse> searchEmployeeByDesignation(String designation) {
        return this.employeeRepository.findByDesignationContainingIgnoreCase(designation)
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public EmployeeResponse updateEmployee(String employeeId, EmployeeRequest employeeRequest) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String currentRole = authentication.getAuthorities().iterator().next().getAuthority();
        if (currentRole.equals("ROLE_EMPLOYEE")) {
            Optional.ofNullable(employeeRequest.getFirstName()).ifPresent(employee::setFirstName);
            Optional.ofNullable(employeeRequest.getLastName()).ifPresent(employee::setLastName);
            Optional.ofNullable(employeeRequest.getPhoneNumber()).ifPresent(employee::setPhoneNumber);
            Optional.ofNullable(employeeRequest.getGender()).ifPresent(employee::setGender);
            Optional.ofNullable(employeeRequest.getDateOfBirth()).ifPresent(employee::setDateOfBirth);

        } else if (currentRole.equals("ROLE_HR")) {
            Optional.ofNullable(employeeRequest.getDesignation()).ifPresent(employee::setDesignation);
            Optional.ofNullable(employeeRequest.getDepartmentId()).ifPresent(deptId -> {
                Department department = departmentRepository.findById(deptId)
                        .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));
                employee.setDepartment(department);
            });

        } else if (currentRole.equals("ROLE_ADMIN")) {
            Optional.ofNullable(employeeRequest.getFirstName()).ifPresent(employee::setFirstName);
            Optional.ofNullable(employeeRequest.getLastName()).ifPresent(employee::setLastName);
            Optional.ofNullable(employeeRequest.getPhoneNumber()).ifPresent(employee::setPhoneNumber);
            Optional.ofNullable(employeeRequest.getGender()).ifPresent(employee::setGender);
            Optional.ofNullable(employeeRequest.getDateOfBirth()).ifPresent(employee::setDateOfBirth);
            Optional.ofNullable(employeeRequest.getJoiningDate()).ifPresent(employee::setJoiningDate);
            Optional.ofNullable(employeeRequest.getDesignation()).ifPresent(employee::setDesignation);
            Optional.ofNullable(employeeRequest.getSalary()).ifPresent(employee::setSalary);
            Optional.ofNullable(employeeRequest.getStatus()).ifPresent(employee::setStatus);
            Optional.ofNullable(employeeRequest.getDepartmentId()).ifPresent(deptId -> {
                Department department = departmentRepository.findById(deptId)
                        .orElseThrow(() -> new ResourceNotFoundExceptions("Department not found !"));
                employee.setDepartment(department);
            });
        }

        Employee updated = this.employeeRepository.save(employee);
        return EmployeeMapper.toResponse(updated);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CustomApiResponse deleteEmployee(String employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

        this.employeeRepository.delete(employee);
        return CustomApiResponse.builder().message("Employee successfully deleted").build();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public CustomApiResponse updateEmployeeStatus(String employeeId, EmployeeStatus status) {
        Employee employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

        employee.setStatus(status);
        if (status == EmployeeStatus.ACTIVE) {
            employee.getUser().setIsActive(true);
        } else {
            employee.getUser().setIsActive(false);
        }

        this.employeeRepository.save(employee);
        return CustomApiResponse.builder().message("Employee Status Changed").build();
    }

    @Override
    public List<EmployeeResponse> findByDepartmentsByEmployee(String departmentId) {
        return this.employeeRepository.findByDepartment_DepartmentId(departmentId)
                .stream()
                .map(EmployeeMapper::toResponse)
                .collect(Collectors.toList());
    }
}
