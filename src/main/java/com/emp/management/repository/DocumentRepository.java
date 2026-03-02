package com.emp.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.management.entities.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {

    List<Document> findByEmployee_EmployeeId(String employeeId);

    List<Document> findByDocumentType(String documentType);
}
