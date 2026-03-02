package com.emp.management.mapper;

import com.emp.management.dto.request.DocumentRequest;
import com.emp.management.dto.response.DocumentResponse;
import com.emp.management.entities.Document;
import com.emp.management.entities.Employee;

public class DocumentMapper {

    public static Document toEntity(DocumentRequest documentRequest, Employee employee) {
        return Document.builder()
                .documentName(documentRequest.getDocumentName())
                .employee(employee)
                .build();
    }

    public static DocumentResponse toResponse(Document document) {
        return DocumentResponse.builder()
                .documentId(document.getDocumentId())
                .documentName(document.getDocumentName())
                .documentType(document.getDocumentType())
                .uploadedAt(document.getUploadedAt())
                .filePath(document.getFilePath())
                .employeeName(document.getEmployee().getFirstName() + " " + document.getEmployee().getLastName())
                .build();
    }
}
