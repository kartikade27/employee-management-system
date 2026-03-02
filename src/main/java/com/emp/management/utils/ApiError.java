package com.emp.management.utils;

import java.time.Instant;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
@AllArgsConstructor
@Builder

public class ApiError {

    private final Instant timestamp;

    private final int status;

    private final String error;

    private final String message;

    private final String path;

    @Builder.Default
    private final Map<String,String>validationErrors = null;
}
