package com.emp.management.service;

import java.io.IOException;
import java.util.List;

import com.emp.management.dto.request.DocumentRequest;
import com.emp.management.dto.response.DocumentResponse;
import com.emp.management.utils.CustomApiResponse;

public interface DocumentService {

    DocumentResponse uploadDocument(DocumentRequest documentRequest)throws IOException;

    List<DocumentResponse> getDocumentsByEmployee(String employeeId);

    List<DocumentResponse> getDocumentsByType(String documentType);

    CustomApiResponse deleteDocument(String documentId)throws IOException;

    DocumentResponse downloadDocument(String documentId, String employeeId);
}
