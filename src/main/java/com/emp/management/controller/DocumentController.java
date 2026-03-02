package com.emp.management.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.emp.management.dto.request.DocumentRequest;
import com.emp.management.dto.response.DocumentResponse;
import com.emp.management.service.DocumentService;
import com.emp.management.utils.CustomApiResponse;

import lombok.RequiredArgsConstructor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
@Tag(name = "Document Management", description = "APIs for Employee Documents (Admin, HR and Employee role based)")
@SecurityRequirement(name = "bearerAuth")
public class DocumentController {

    private final DocumentService documentService;

    // ================= UPLOAD DOCUMENT =================
    @PostMapping(value = "/uploadDocument", consumes = "multipart/form-data")
    @Operation(summary = "Upload document", description = "Only ADMIN and HR can upload documents for employees")
    public ResponseEntity<DocumentResponse> uploadDocument(
            @ModelAttribute DocumentRequest documentRequest) throws IOException {
        return ResponseEntity.ok(documentService.uploadDocument(documentRequest));
    }

    // ================= GET DOCUMENTS BY EMPLOYEE =================
    @GetMapping("/getDocumentsByEmployee/{employeeId}")
    @Operation(summary = "Get documents by employee", description = "ADMIN, HR or EMPLOYEE (can view only own documents)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documents fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Unauthorized access")
    })
    public ResponseEntity<List<DocumentResponse>> getDocumentsByEmployee(@PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.getDocumentsByEmployee(employeeId));
    }

    // ================= GET DOCUMENTS BY TYPE =================
    @GetMapping("/getDocumentByType")
    @Operation(summary = "Get documents by type", description = "Only ADMIN and HR can filter documents by type")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Documents fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed")
    })
    public ResponseEntity<List<DocumentResponse>> getDocumentsByType(@RequestParam String documentType) {
        return ResponseEntity.ok(documentService.getDocumentsByType(documentType));
    }

    // ================= DELETE DOCUMENT =================
    @DeleteMapping("/deleteDocument/{documentId}")
    @Operation(summary = "Delete document", description = "Only ADMIN and HR can delete employee documents")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Only ADMIN and HR allowed"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    public ResponseEntity<CustomApiResponse> deleteDocument(@PathVariable String documentId) throws IOException {
        return ResponseEntity.ok(documentService.deleteDocument(documentId));
    }

    // ================= DOWNLOAD DOCUMENT =================
    @GetMapping("/downloadDocument/{documentId}/{employeeId}")
    @Operation(summary = "Download document", description = "Only EMPLOYEE can download own document")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Document downloaded successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Document not found")
    })
    public ResponseEntity<DocumentResponse> downloadDocument(
            @PathVariable String documentId,
            @PathVariable String employeeId) {
        return ResponseEntity.ok(documentService.downloadDocument(documentId, employeeId));
    }

}