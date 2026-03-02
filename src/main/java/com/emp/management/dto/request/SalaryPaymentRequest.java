package com.emp.management.dto.request;

import com.emp.management.entities.PaymentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class SalaryPaymentRequest {

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Month-Year is required")
    private String monthYear;
    @Positive(message = "Amount must be positive")
    private Double amount;

    private PaymentStatus status;
}
