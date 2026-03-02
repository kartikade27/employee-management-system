package com.emp.management.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.SalaryPaymentRequest;
import com.emp.management.dto.response.SalaryPaymentResponse;
import com.emp.management.entities.Employee;
import com.emp.management.entities.PaymentStatus;
import com.emp.management.entities.SalaryPayment;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.SalaryPaymentMapper;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.repository.SalaryPaymentRepository;
import com.emp.management.service.SalaryPaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalaryPaymentServiceImpl implements SalaryPaymentService {

    private final SalaryPaymentRepository paymentRepository;

    private final EmployeeRepository employeeRepository;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public SalaryPaymentResponse generateSalary(SalaryPaymentRequest paymentRequest) {
        Employee employee = this.employeeRepository.findById(paymentRequest.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));
        employee.setSalary(paymentRequest.getAmount());

        SalaryPayment entity = SalaryPaymentMapper.toEntity(paymentRequest, employee);
        entity.setEmployee(employee);
        SalaryPayment salaryPaymentResponse = this.paymentRepository.save(entity);
        return SalaryPaymentMapper.toResponse(salaryPaymentResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public List<SalaryPaymentResponse> getSalaryByEmployee(String employeeId) {
        return this.paymentRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(SalaryPaymentMapper::toResponse)
                .collect(
                        Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<SalaryPaymentResponse> getSalaryByStatus(PaymentStatus status) {
        return this.paymentRepository.findByStatus(status)
                .stream()
                .map(SalaryPaymentMapper::toResponse)
                .collect(
                        Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR','EMPLOYEE')")
    public List<SalaryPaymentResponse> getSalaryByMonth(String monthYear) {
        return this.paymentRepository.findByMonthYear(monthYear)
                .stream()
                .map(SalaryPaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double totalSalaryPaid() {
        Double totalSalaryPaid = this.paymentRepository.findTotalSalaryPaid();
        return totalSalaryPaid;
    }

}
