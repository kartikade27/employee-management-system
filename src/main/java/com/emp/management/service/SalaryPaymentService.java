package com.emp.management.service;

import java.util.List;

import com.emp.management.dto.request.SalaryPaymentRequest;
import com.emp.management.dto.response.SalaryPaymentResponse;
import com.emp.management.entities.PaymentStatus;

public interface SalaryPaymentService {

    SalaryPaymentResponse generateSalary(SalaryPaymentRequest paymentRequest);

    List<SalaryPaymentResponse> getSalaryByEmployee(String employeeId);

    List<SalaryPaymentResponse> getSalaryByStatus(PaymentStatus status);

    List<SalaryPaymentResponse> getSalaryByMonth(String monthYear);

    Double totalSalaryPaid();
}
