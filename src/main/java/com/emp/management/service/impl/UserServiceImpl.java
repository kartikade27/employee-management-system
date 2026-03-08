package com.emp.management.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emp.management.config.CustomUserDetails;
import com.emp.management.dto.request.LoginRequest;
import com.emp.management.dto.request.RegisterRequest;
import com.emp.management.dto.response.JwtResponse;
import com.emp.management.dto.response.UserResponse;
import com.emp.management.entities.Role;
import com.emp.management.entities.User;
import com.emp.management.exceptions.ResourceAlreadyExistsExceptions;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.jwt.JwtService;
import com.emp.management.mapper.UserMapper;
import com.emp.management.repository.UserRepository;
import com.emp.management.service.UserService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public UserResponse registerUser(RegisterRequest request) {
        if (this.userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsExceptions("User already Exists !");
        }

        User entity = UserMapper.toEntity(request);

        entity.setPassword(this.passwordEncoder.encode(entity.getPassword()));
        entity.setRole(request.getRole() != null ? request.getRole() : Role.EMPLOYEE);
        User save = this.userRepository.save(entity);

        return UserMapper.toResponse(save);
    }

    @Override
    public JwtResponse loginUser(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password !");
        } catch (DisabledException e) {
            throw new DisabledException("Your Account is disabled.please contact admin");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();

        String accessToken = this.jwtService.generateAccessToken(userDetails);
        String refreshToken = this.jwtService.generateRefreshToken(userDetails);
        UserResponse response = UserMapper.toResponse(user);
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .response(response)
                .build();
    }

    @Override
    public JwtResponse refreshToken(String token) {
        if (!jwtService.isRefreshToken(token)) {
            throw new AccessDeniedException("Unauthorized");
        }
        String username = this.jwtService.extractUsername(token);
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new AccessDeniedException("Unauthorized"));
        CustomUserDetails userDetails = new CustomUserDetails(user);
        if (!jwtService.isTokenValid(token, userDetails)) {
            throw new AccessDeniedException("Refresh token expired !");
        }

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = this.jwtService.generateRefreshToken(userDetails);
        UserResponse response = UserMapper.toResponse(user);
        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .response(response)
                .build();

    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CustomApiResponse deActivateUserAccount(String userId) {

        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found"));

        if (user.getRole().equals(Role.ADMIN)) {
            throw new ResourceAlreadyExistsExceptions("Admin account cannot be deactivated");
        }

        if (!user.getIsActive()) {
            throw new ResourceAlreadyExistsExceptions("User account is already deactivated");
        }

        user.setIsActive(false);
        user.setUpdateAt(LocalDate.now());
        this.userRepository.save(user);

        return CustomApiResponse.builder()
                .message("User account deactivated successfully")
                .build();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CustomApiResponse activateUserAccount(String userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("User not found "));

        if (user.getIsActive()) {
            throw new ResourceAlreadyExistsExceptions("User account is already activated");
        }
        user.setIsActive(true);
        user.setUpdateAt(LocalDate.now());
        this.userRepository.save(user);

        return CustomApiResponse.builder().message("User account activated successfully").build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

}
