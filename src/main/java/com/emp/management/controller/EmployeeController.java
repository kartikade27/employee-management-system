package com.emp.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.EmployeeRequest;
import com.emp.management.dto.response.EmployeeResponse;
import com.emp.management.entities.EmployeeStatus;
import com.emp.management.service.EmployeeService;
import com.emp.management.utils.CustomApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Management", description = "APIs for managing employees (Admin, HR, Employee role based access)")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/createEmployee")
    @Operation(summary = "Create Employee", description = "Only ADMIN and HR can create new employee")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee created successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "400", description = "Validation error")
    })
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.ok(employeeService.createEmployee(request));
    }

    @GetMapping("/getEmployeeById/{employeeId}")
    @Operation(summary = "Get Employee by ID", description = "ADMIN, HR or EMPLOYEE (own data only)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Employee not found"),
        @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @GetMapping("/getAllEmployee")
    @Operation(summary = "Get all employees", description = "Only ADMIN and HR can access all employees")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee list fetched"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/getEmployeeByStatus")
    @Operation(summary = "Get employees by status", description = "Only ADMIN and HR can filter employees by status")
    public ResponseEntity<List<EmployeeResponse>> getEmployeeByStatus(@RequestParam EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.getEmployeeByStatus(status));
    }

    @GetMapping("/searchEmployee")
    @Operation(summary = "Search employee by first name", description = "Only ADMIN and HR can search employees")
    public ResponseEntity<List<EmployeeResponse>> searchEmployeeByFirstName(@RequestParam String firstName) {
        return ResponseEntity.ok(employeeService.searchEmployeeByFirstName(firstName));
    }

    @GetMapping("/searchEmployeeByDesignation")
    @Operation(summary = "Search employee by designation", description = "Only ADMIN and HR can search employees by designation")
    public ResponseEntity<List<EmployeeResponse>> searchEmployeeByDesignation(@RequestParam String designation) {
        return ResponseEntity.ok(employeeService.searchEmployeeByDesignation(designation));
    }

    @PutMapping("/updateEmployee/{employeeId}")
    @Operation(summary = "Update employee", description = "ADMIN: full update, HR: department & designation, EMPLOYEE: own personal details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee updated successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable String employeeId,
            @Valid @RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employeeRequest));
    }

    @DeleteMapping("/deleteEmployee/{employeeId}")
    @Operation(summary = "Delete employee", description = "Only ADMIN can delete employee")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Employee deleted"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN allowed")
    })
    public ResponseEntity<CustomApiResponse> deleteEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(employeeService.deleteEmployee(employeeId));
    }

    @PatchMapping("/updateEmployeeStatus/{employeeId}")
    @Operation(summary = "Update employee status", description = "Only ADMIN and HR can change employee status")
    public ResponseEntity<CustomApiResponse> updateEmployeeStatus(
            @PathVariable String employeeId,
            @RequestParam EmployeeStatus status) {
        return ResponseEntity.ok(employeeService.updateEmployeeStatus(employeeId, status));
    }

    @GetMapping("/findByDepartmentsByEmployee/{departmentId}")
    @Operation(summary = "Get employees by department", description = "Fetch employees of a specific department")
    public ResponseEntity<List<EmployeeResponse>> findByDepartmentsByEmployee(@PathVariable String departmentId) {
        return ResponseEntity.ok(employeeService.findByDepartmentsByEmployee(departmentId));
    }
}