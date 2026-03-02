package com.emp.management.utils;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.emp.management.entities.Role;
import com.emp.management.entities.User;
import com.emp.management.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> admin = this.userRepository.findByEmail("kartikade27@gmail.com");
        if (admin.isEmpty()) {
            User user = User.builder()
                    .username("Kartik ade")
                    .email("kartikade27@gmail.com")
                    .password(this.passwordEncoder.encode("Kartik$@123"))
                    .role(Role.ADMIN)
                    .build();
            this.userRepository.save(user);
        }
    }

}
