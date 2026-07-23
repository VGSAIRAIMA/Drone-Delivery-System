package com.example.droneservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionalHandler {

    @ExceptionHandler(DroneNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDroneNotFoundException(DroneNotFoundException ex){
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setErrorType("NOT_FOUND");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler(DroneNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleDroneNotAvailableException(DroneNotAvailableException ex){
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setErrorType("BAD_REQUEST");
        response.setErrorMessage(ex.getMessage());
        response.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
/*Notes:  Why is it called "Binding Result"?The name comes from a core web development concept called Data Binding.
When a user types data into a web form or sends a JSON object, it arrives at your server as raw text.
Spring Boot has to take that raw text and "bind" (map/connect) it to a real Java object.Data Binding:
The act of gluing incoming text data into Java object properties.
Binding Result: The final report card of how that gluing process went.It answers two questions for Spring Boot:
Did the text successfully convert into Java types? (e.g., did they type letters into an Age field that expects integers?)
Did the values pass your validation rules? (e.g., did they leave the Email blank?)
The BindingResult is literally the "Result of trying to bind the data."*/