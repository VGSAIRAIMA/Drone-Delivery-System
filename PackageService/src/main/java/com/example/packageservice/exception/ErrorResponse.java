package com.example.packageservice.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private Integer status;
    private String errorType;
    private String errorMessage;
    private LocalDateTime timeStamp;
}
