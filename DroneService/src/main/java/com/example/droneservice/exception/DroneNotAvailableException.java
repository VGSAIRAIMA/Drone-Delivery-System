package com.example.droneservice.exception;

public class DroneNotAvailableException extends RuntimeException{
    public DroneNotAvailableException(String msg){
        super(msg);
    }
}
