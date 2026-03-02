package com.emp.management.service;

import java.util.List;

import com.emp.management.dto.request.LoginRequest;
import com.emp.management.dto.request.RegisterRequest;
import com.emp.management.dto.response.JwtResponse;
import com.emp.management.dto.response.UserResponse;
import com.emp.management.utils.CustomApiResponse;

public interface UserService {

    UserResponse registerUser(RegisterRequest request);

    JwtResponse loginUser(LoginRequest loginRequest);

    JwtResponse refreshToken(String token);

    CustomApiResponse deActivateUserAccount(String userId);

    CustomApiResponse activateUserAccount(String userId);

    List<UserResponse> getAllUsers();

}
