package com.emp.management.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentUploaderService {

    private final Cloudinary cloudinary;

    public Map<String, String> uploadDocuments(MultipartFile file, String folder) throws IOException {
        @SuppressWarnings("unchecked")
        Map<String, Object> upload = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", "uploads",
                "resource_type", "auto"));
        Map<String, String> response = new HashMap<>();
        response.put("secure_url", upload.get("secure_url").toString());
        response.put("public_id", upload.get("public_id").toString());
        
        return response;
    }

    public void deleteDocument(String publicId) throws IOException {
        this.cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
