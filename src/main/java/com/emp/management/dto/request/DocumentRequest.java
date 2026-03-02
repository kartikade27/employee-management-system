package com.emp.management.dto.request;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Document Upload Request")
public class DocumentRequest {

    @NotBlank(message = "Document name is required !")
    @Schema(example = "Aadhar Card")
    private String documentName;

    @NotBlank(message = "Employee is required !")
    @Schema(example = "EMP001")
    private String employeeId;

    @Schema(type = "string", format = "binary", description = "Upload file")
    private MultipartFile file;
}