package com.example.packageservice.exception;


public class PackageNotFoundException extends RuntimeException{
    public PackageNotFoundException(String msg) {
        super(msg);
    }
}
