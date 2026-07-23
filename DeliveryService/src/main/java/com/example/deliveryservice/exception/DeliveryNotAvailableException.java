package com.example.deliveryservice.exception;

public class DeliveryNotAvailableException extends RuntimeException {
    public DeliveryNotAvailableException(String message) {
        super(message);
    }
}
