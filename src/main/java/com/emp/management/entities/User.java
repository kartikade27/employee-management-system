package com.emp.management.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private Boolean isActive = true;

    private LocalDate createAt;

    private LocalDate updateAt;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Employee employee;

    @PrePersist
    void onCreate() {
        this.userId = UUID.randomUUID().toString();
        this.createAt = LocalDate.now();
    }

    @PreUpdate
    void onUpdate() {
        this.updateAt = LocalDate.now();
    }
}
