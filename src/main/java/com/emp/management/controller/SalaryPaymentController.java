package com.emp.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.SalaryPaymentRequest;
import com.emp.management.dto.response.SalaryPaymentResponse;
import com.emp.management.entities.PaymentStatus;
import com.emp.management.service.SalaryPaymentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/salaryPayments")
@Tag(name = "Salary Management", description = "APIs for Salary Generation and Payments (Admin, HR, Employee role based)")
@SecurityRequirement(name = "bearerAuth")
public class SalaryPaymentController {

    private final SalaryPaymentService salaryPaymentService;

    // ADMIN / HR
    @PostMapping("/generateSalary")
    @Operation(
        summary = "Generate salary",
        description = "Only ADMIN and HR can generate salary for employees"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Salary generated successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<SalaryPaymentResponse> generateSalary(
            @Valid @RequestBody SalaryPaymentRequest paymentRequest) {
        return ResponseEntity.ok(salaryPaymentService.generateSalary(paymentRequest));
    }

    // ADMIN / HR / EMPLOYEE (own data)
    @GetMapping("/getSalaryByEmployee/{employeeId}")
    @Operation(
        summary = "Get salary by employee",
        description = "ADMIN, HR or EMPLOYEE (can view only own salary)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Salary fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized access"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<List<SalaryPaymentResponse>> getSalaryByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(salaryPaymentService.getSalaryByEmployee(employeeId));
    }

    // ADMIN / HR
    @GetMapping("/getSalaryByStatus")
    @Operation(
        summary = "Get salary by status",
        description = "Only ADMIN and HR can filter salary by payment status"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Salary fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed")
    })
    public ResponseEntity<List<SalaryPaymentResponse>> getSalaryByStatus(@RequestParam PaymentStatus status) {
        return ResponseEntity.ok(salaryPaymentService.getSalaryByStatus(status));
    }

    // ADMIN / HR / EMPLOYEE
    @GetMapping("/getSalaryByMonth")
    @Operation(
        summary = "Get salary by month",
        description = "ADMIN, HR and EMPLOYEE can view salary by month"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Salary fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<List<SalaryPaymentResponse>> getSalaryByMonth(@RequestParam String monthYear) {
        return ResponseEntity.ok(salaryPaymentService.getSalaryByMonth(monthYear));
    }

    // ADMIN / HR
    @GetMapping("/totalSalaryPaid")
    @Operation(
        summary = "Get total salary paid",
        description = "Only ADMIN and HR can view total salary paid"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Total salary calculated successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed")
    })
    public ResponseEntity<Double> totalSalaryPaid() {
        return ResponseEntity.ok(salaryPaymentService.totalSalaryPaid());
    }

}