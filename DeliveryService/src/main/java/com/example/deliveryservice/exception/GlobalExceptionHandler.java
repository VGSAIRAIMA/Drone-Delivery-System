package com.example.deliveryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeliveryNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleDeliveryNotFoundException(
            DeliveryNotAvailableException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setErrorType("DELIVERY_NOT_FOUND");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PackageNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handlePackageNotAvailableException(
            PackageNotAvailableException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setErrorType("PACKAGE_NOT_AVAILABLE");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RouteNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleRouteNotAvailableException(
            RouteNotAvailableException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setErrorType("ROUTE_NOT_AVAILABLE");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DroneNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleDroneNotAvailableException(
            DroneNotAvailableException ex) {

        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setErrorType("DRONE_NOT_AVAILABLE");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(),
                                error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}