package com.emp.management.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("ownershipService")
public class OwnershipService {

    public boolean isUserOwner(String employeeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        CustomUserDetails loggedUser = (CustomUserDetails) authentication.getPrincipal();
        String loggedUserId = loggedUser.getUser().getEmployee().getEmployeeId();
        return loggedUserId.equals(employeeId);

    }
}
