package com.example.routeservice.Exception;

public class RouteNotFoundException extends RuntimeException{
    public RouteNotFoundException(String msg){
        super(msg);
    }
}

