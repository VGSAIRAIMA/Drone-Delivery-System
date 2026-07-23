package com.example.droneservice.exception;

public class DroneNotFoundException extends RuntimeException{
    public DroneNotFoundException(String msg){
        super(msg);
    }
}
