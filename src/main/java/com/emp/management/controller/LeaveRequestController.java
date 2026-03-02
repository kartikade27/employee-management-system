package com.emp.management.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.LeaveRequestDTO;
import com.emp.management.dto.response.LeaveResponse;
import com.emp.management.entities.LeaveStatus;
import com.emp.management.service.LeaveRequestService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/leavesRequest")
@RequiredArgsConstructor
@Tag(name = "Leave Management", description = "APIs for Leave Requests (Employee, HR, Admin role based access)")
@SecurityRequirement(name = "bearerAuth")
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    // EMPLOYEE
    @PostMapping("/applyLeave")
    @Operation(
        summary = "Apply for leave",
        description = "Only EMPLOYEE can apply for leave"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leave applied successfully"),
        @ApiResponse(responseCode = "403", description = "Only EMPLOYEE allowed"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<LeaveResponse> applyLeave(@Valid @RequestBody LeaveRequestDTO requestDTO) {
        return ResponseEntity.ok(leaveRequestService.applyLeave(requestDTO));
    }

    // ADMIN / HR / EMPLOYEE (own data)
    @GetMapping("/getLeavesByEmployee/{employeeId}")
    @Operation(
        summary = "Get leaves by employee",
        description = "ADMIN, HR or EMPLOYEE (only own leaves)"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leaves fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized access"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    public ResponseEntity<List<LeaveResponse>> getLeavesByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(leaveRequestService.getLeavesByEmployee(employeeId));
    }

    // ADMIN / HR
    @GetMapping("/getLeavesByStatus")
    @Operation(
        summary = "Get leaves by status",
        description = "Only ADMIN and HR can filter leaves by status"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leaves fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed")
    })
    public ResponseEntity<List<LeaveResponse>> getLeavesByStatus(@RequestParam LeaveStatus status) {
        return ResponseEntity.ok(leaveRequestService.getLeavesByStatus(status));
    }

    // ADMIN / HR
    @PatchMapping("/approveLeave/{leaveId}")
    @Operation(
        summary = "Approve leave request",
        description = "Only ADMIN and HR can approve leave"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leave approved successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed"),
        @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    public ResponseEntity<LeaveResponse> approveLeave(@PathVariable String leaveId) {
        return ResponseEntity.ok(leaveRequestService.approveLeave(leaveId));
    }

    // ADMIN / HR
    @PatchMapping("/rejectLeave/{leaveId}")
    @Operation(
        summary = "Reject leave request",
        description = "Only ADMIN and HR can reject leave"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Leave rejected successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed"),
        @ApiResponse(responseCode = "404", description = "Leave request not found")
    })
    public ResponseEntity<LeaveResponse> rejectLeave(@PathVariable String leaveId) {
        return ResponseEntity.ok(leaveRequestService.rejectLeave(leaveId));
    }

}