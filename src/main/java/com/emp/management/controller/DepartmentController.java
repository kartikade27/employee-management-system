package com.emp.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.DepartmentRequest;
import com.emp.management.dto.response.DepartmentResponse;
import com.emp.management.service.DepartmentService;
import com.emp.management.utils.CustomApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// Swagger imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/departments")
@Tag(name = "Department Management", description = "APIs for managing departments (Admin & HR role based access)")
public class DepartmentController {

    private final DepartmentService departmentService;

    // ================= CREATE DEPARTMENT =================
    @PostMapping("/createDepartment")
    @Operation(
        summary = "Create Department",
        description = "Only ADMIN can create a new department",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department created successfully"),
        @ApiResponse(responseCode = "400", description = "Department already exists"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<DepartmentResponse> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        DepartmentResponse department = this.departmentService.createDepartment(request);
        return ResponseEntity.ok(department);
    }

    // ================= GET DEPARTMENT BY ID =================
    @GetMapping("/getDepartment/{departmentId}")
    @Operation(
        summary = "Get Department by ID",
        description = "Accessible by ADMIN and HR",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable String departmentId) {
        DepartmentResponse departmentById = this.departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(departmentById);
    }

    // ================= GET ALL DEPARTMENTS =================
    @GetMapping("/getAllDepartments")
    @Operation(
        summary = "Get all departments",
        description = "Accessible by ADMIN and HR",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Departments fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {
        List<DepartmentResponse> allDepartments = this.departmentService.getAllDepartments();
        return ResponseEntity.ok(allDepartments);
    }

    // ================= SEARCH DEPARTMENT =================
    @GetMapping("/searchDepartment")
    @Operation(
        summary = "Search department by name",
        description = "Only ADMIN can search departments by name",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Departments found"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<DepartmentResponse>> searchDepartmentByName(@RequestParam String name) {
        List<DepartmentResponse> searchDepartmentByName = this.departmentService.searchDepartmentByName(name);
        return ResponseEntity.ok(searchDepartmentByName);
    }

    // ================= UPDATE DEPARTMENT =================
    @PutMapping("/updateDepartment/{departmentId}")
    @Operation(
        summary = "Update department",
        description = "Only ADMIN can update a department",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department updated successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable String departmentId,
            @Valid @RequestBody DepartmentRequest departmentRequest) {

        DepartmentResponse updateDepartment = this.departmentService.updateDepartment(departmentId, departmentRequest);
        return ResponseEntity.ok(updateDepartment);
    }

    // ================= DELETE DEPARTMENT =================
    @DeleteMapping("/deleteDepartment/{departmentId}")
    @Operation(
        summary = "Delete department",
        description = "Only ADMIN can delete a department",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Department deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<CustomApiResponse> deleteDepartment(@PathVariable String departmentId) {
        CustomApiResponse deleteDepartment = this.departmentService.deleteDepartment(departmentId);
        return ResponseEntity.ok(deleteDepartment);
    }
}