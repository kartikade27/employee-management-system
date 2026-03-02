package com.emp.management.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentResponse {

    private String documentId;

    private String documentName;

    private String documentType;

    private LocalDate uploadedAt;

    private String filePath;

    private String employeeName;
}
