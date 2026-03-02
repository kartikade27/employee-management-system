package com.emp.management.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.AttendanceRequest;
import com.emp.management.dto.response.AttendanceResponse;
import com.emp.management.service.AttendanceService;
import com.emp.management.utils.CustomApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/attendances")
@RequiredArgsConstructor
@Tag(name = "Attendance Management", description = "APIs for Attendance (Employee, HR, Admin role based access)")
@SecurityRequirement(name = "bearerAuth")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/markAttendance")
    @Operation(
        summary = "Mark attendance",
        description = "Only EMPLOYEE can mark their attendance"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Attendance marked successfully"),
        @ApiResponse(responseCode = "400", description = "Attendance already marked"),
        @ApiResponse(responseCode = "403", description = "Only EMPLOYEE allowed")
    })
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        return ResponseEntity.ok(attendanceService.markAttendance(request));
    }

    @GetMapping("/getAttendanceByEmployee/{employeeId}")
    @Operation(
        summary = "Get attendance by employee",
        description = "ADMIN, HR or EMPLOYEE (own attendance only)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Attendance fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Attendance not found"),
        @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.getAttendanceByEmployee(employeeId));
    }

    @GetMapping("/getAttendanceByDate")
    @Operation(
        summary = "Get attendance by date",
        description = "Only ADMIN and HR can view attendance by date"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Attendance list fetched"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed")
    })
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(attendanceService.getAttendanceByDate(date));
    }

    @GetMapping("/getAttendanceEmployeeAndDate/{employeeId}")
    @Operation(
        summary = "Get attendance by employee and date",
        description = "ADMIN, HR or EMPLOYEE (own attendance only)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Attendance fetched successfully"),
        @ApiResponse(responseCode = "404", description = "Attendance not found"),
        @ApiResponse(responseCode = "403", description = "Unauthorized")
    })
    public ResponseEntity<AttendanceResponse> getAttendanceEmployeeAndDate(
            @PathVariable String employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceEmployeeAndDate(employeeId, date)
        );
    }

    @PatchMapping("/checkout/{employeeId}")
    @Operation(
        summary = "Employee checkout",
        description = "Only EMPLOYEE can checkout their attendance"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Checkout successful"),
        @ApiResponse(responseCode = "403", description = "Only EMPLOYEE allowed"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<CustomApiResponse> checkOut(@PathVariable String employeeId) {
        return ResponseEntity.ok(attendanceService.checkOutTime(employeeId));
    }

}