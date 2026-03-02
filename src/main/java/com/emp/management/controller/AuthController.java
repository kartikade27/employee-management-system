package com.emp.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.LoginRequest;
import com.emp.management.dto.request.RegisterRequest;
import com.emp.management.dto.response.JwtResponse;
import com.emp.management.dto.response.UserResponse;
import com.emp.management.service.UserService;
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
@RequestMapping("/api/auth")
@Tag(name = "Authentication & User Management", description = "APIs for Login, Registration and User Account Management")
public class AuthController {

    private final UserService userService;

    // ================= REGISTER =================
    @PostMapping("/register")
    @Operation(
        summary = "Register new user",
        description = "Only ADMIN or HR can register a new user",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "User already exists"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        UserResponse registerUser = this.userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUser);
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    @Operation(
        summary = "Login user",
        description = "Login using email and password. Returns JWT access token and refresh token"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "403", description = "Account disabled")
    })
    public ResponseEntity<JwtResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse loginUser = this.userService.loginUser(loginRequest);
        return ResponseEntity.ok(loginUser);
    }

    // ================= REFRESH TOKEN =================
    @PostMapping("/refreshToken")
    @Operation(
        summary = "Refresh access token",
        description = "Generate new access token using refresh token"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
        @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<JwtResponse> refreshToken(@RequestParam String token) {
        JwtResponse refreshToken = this.userService.refreshToken(token);
        return ResponseEntity.ok(refreshToken);
    }

    // ================= DEACTIVATE USER =================
    @PatchMapping("/deactivate-user-account/{userId}")
    @Operation(
        summary = "Deactivate user account",
        description = "Only ADMIN can deactivate a user account",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User deactivated successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN can access"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomApiResponse> deActivateUserAccount(@PathVariable String userId) {
        CustomApiResponse deActivateUserAccount = this.userService.deActivateUserAccount(userId);
        return ResponseEntity.ok(deActivateUserAccount);
    }

    // ================= ACTIVATE USER =================
    @PatchMapping("/activate-user-account/{userId}")
    @Operation(
        summary = "Activate user account",
        description = "Only ADMIN can activate a user account",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User activated successfully"),
        @ApiResponse(responseCode = "403", description = "Only ADMIN can access"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<CustomApiResponse> activateUserAccount(@PathVariable String userId) {
        CustomApiResponse activateUserAccount = this.userService.activateUserAccount(userId);
        return ResponseEntity.ok(activateUserAccount);
    }

    // ================= GET ALL USERS =================
    @GetMapping("/findAllUsers")
    @Operation(
        summary = "Get all users",
        description = "Accessible by ADMIN and HR",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        List<UserResponse> allUsers = this.userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}