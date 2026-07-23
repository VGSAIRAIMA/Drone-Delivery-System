package com.example.deliveryservice.exception;

public class DroneNotAvailableException extends RuntimeException{
    public DroneNotAvailableException(String msg){
        super(msg);
    }
}
