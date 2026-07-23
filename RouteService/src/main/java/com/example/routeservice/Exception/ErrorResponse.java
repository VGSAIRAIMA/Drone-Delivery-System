package com.example.routeservice.Exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private Integer status;
    private String errorType;
    private String errorMessage;
    private LocalDateTime timeStamp;
    private Map<String,String> validationErrors;
}