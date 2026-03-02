package com.emp.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.emp.management.entities.PaymentStatus;
import com.emp.management.entities.SalaryPayment;

public interface SalaryPaymentRepository extends JpaRepository<SalaryPayment, String> {

   List<SalaryPayment> findByEmployee_EmployeeId(String employeeId);

   List<SalaryPayment> findByStatus(PaymentStatus status);

   @Query("SELECT s FROM SalaryPayment s WHERE s.monthYear LIKE CONCAT(:monthYear, '%')")
   List<SalaryPayment> findByMonthYear(@Param("monthYear") String monthYear);

   @Query("SELECT SUM(s.amount) FROM SalaryPayment s WHERE s.status = com.emp.management.entities.PaymentStatus.PAID")
   Double findTotalSalaryPaid();

}
