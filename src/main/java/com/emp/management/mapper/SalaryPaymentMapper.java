package com.emp.management.mapper;

import com.emp.management.dto.request.SalaryPaymentRequest;
import com.emp.management.dto.response.SalaryPaymentResponse;
import com.emp.management.entities.Employee;
import com.emp.management.entities.SalaryPayment;

public class SalaryPaymentMapper {

    public static SalaryPayment toEntity(SalaryPaymentRequest dto, Employee employee) {
        return SalaryPayment.builder()
                .monthYear(dto.getMonthYear())
                .amount(dto.getAmount())
                .status(dto.getStatus())
                .employee(employee)
                .build();
    }

    public static SalaryPaymentResponse toResponse(SalaryPayment entity) {
        return SalaryPaymentResponse.builder()
                .paymentId(entity.getPaymentId())
                .employeeName(entity.getEmployee().getFirstName() + " " + entity.getEmployee().getLastName())
                .monthYear(entity.getMonthYear())
                .amount(entity.getAmount())
                .paymentStatus(entity.getStatus().name())
                .build();
    }

}
