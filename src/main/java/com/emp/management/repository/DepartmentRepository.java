package com.emp.management.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.management.entities.Department;


public interface DepartmentRepository extends JpaRepository<Department,String> {

    List<Department> findByDepartmentNameContainingIgnoreCase(String departmentName);

    boolean existsByDepartmentName(String departmentName);


}
