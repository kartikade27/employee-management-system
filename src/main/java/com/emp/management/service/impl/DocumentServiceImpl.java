package com.emp.management.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.emp.management.dto.request.DocumentRequest;
import com.emp.management.dto.response.DocumentResponse;
import com.emp.management.entities.Document;
import com.emp.management.entities.Employee;
import com.emp.management.exceptions.ResourceNotFoundExceptions;
import com.emp.management.mapper.DocumentMapper;
import com.emp.management.repository.DocumentRepository;
import com.emp.management.repository.EmployeeRepository;
import com.emp.management.service.DocumentService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final EmployeeRepository employeeRepository;

    private final DocumentRepository documentRepository;

    private final DocumentUploaderService documentUploaderService;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public DocumentResponse uploadDocument(DocumentRequest documentRequest) throws IOException {
        Employee employee = this.employeeRepository.findById(documentRequest.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundExceptions("Employee not found !"));

        Map<String, String> uploadDocuments = this.documentUploaderService.uploadDocuments(documentRequest.getFile(),
                "Documents");
        String filePath = uploadDocuments.get("secure_url");
        String publicId = uploadDocuments.get("public_id");
        String documentType = this.resolveType(documentRequest.getDocumentName());
        Document entity = DocumentMapper.toEntity(documentRequest, employee);

        entity.setEmployee(employee);
        entity.setDocumentPublicId(publicId);
        entity.setFilePath(filePath);
        entity.setDocumentType(documentType);

        Document documentStored = this.documentRepository.save(entity);

        return DocumentMapper.toResponse(documentStored);

    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or hasRole('HR') or " +
            "(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public List<DocumentResponse> getDocumentsByEmployee(String employeeId) {
        return this.documentRepository.findByEmployee_EmployeeId(employeeId)
                .stream()
                .map(DocumentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public List<DocumentResponse> getDocumentsByType(String documentType) {
        return this.documentRepository.findByDocumentType(documentType)
                .stream()
                .map(DocumentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','HR')")
    public CustomApiResponse deleteDocument(String documentId) throws IOException {
        Document deleteDocument = this.documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Document not found !"));

        if (deleteDocument.getDocumentPublicId() != null) {
            this.documentUploaderService.deleteDocument(deleteDocument.getDocumentPublicId());
            deleteDocument.setFilePath(null);
            deleteDocument.setDocumentPublicId(null);

            this.documentRepository.delete(deleteDocument);
        }

        return CustomApiResponse.builder().message("Document Successfully deleted !").build();
    }

    private String resolveType(String documentName) {
        if (documentName == null)
            return "Other";
        String name = documentName.toLowerCase();
        if (name.contains("offer") || name.contains("appointment") || name.contains("contract")) {
            return "Employment";
        } else if (name.contains("aadhaar") || name.contains("pan") || name.contains("passport")) {
            return "Identity Proof";
        } else if (name.contains("salary") || name.contains("tax") || name.contains("form16")) {
            return "Finance";
        } else if (name.contains("degree") || name.contains("certificate")) {
            return "Education";
        } else {
            return "Other";
        }
    }

    @Override
    @PreAuthorize("(hasRole('EMPLOYEE') and @ownershipService.isUserOwner(#employeeId))")
    public DocumentResponse downloadDocument(String documentId, String employeeId) {

        // ✅ fetch document by documentId (not employeeId)
        Document document = this.documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundExceptions("Document not found !"));

        // ✅ extra safety: check document belongs to that employee
        if (!document.getEmployee().getEmployeeId().equals(employeeId)) {
            throw new ResourceNotFoundExceptions("You are not allowed to download this document");
        }

        return DocumentMapper.toResponse(document);
    }

}
