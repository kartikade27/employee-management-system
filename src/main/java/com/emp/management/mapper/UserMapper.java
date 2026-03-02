package com.emp.management.mapper;

import com.emp.management.dto.request.RegisterRequest;
import com.emp.management.dto.response.UserResponse;
import com.emp.management.entities.User;

public class UserMapper {

    public static User toEntity(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }

    public static UserResponse toResponse(User user) {
       
         return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreateAt())
                .employeeId(
                    user.getEmployee() != null 
                        ? user.getEmployee().getEmployeeId() 
                        : null
                )
                .isActive(user.getIsActive())
                .build();
    }

}
