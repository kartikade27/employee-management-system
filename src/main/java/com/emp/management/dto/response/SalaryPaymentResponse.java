package com.emp.management.dto.response;

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
public class SalaryPaymentResponse {

    private String paymentId;

    private String employeeName;

    private String monthYear;

    private Double amount;

    private String paymentStatus;
}
