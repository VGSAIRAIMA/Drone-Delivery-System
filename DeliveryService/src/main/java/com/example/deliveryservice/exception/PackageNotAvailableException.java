package com.example.deliveryservice.exception;

public class PackageNotAvailableException extends RuntimeException{
    public PackageNotAvailableException(String msg){
        super(msg);
    }
}
