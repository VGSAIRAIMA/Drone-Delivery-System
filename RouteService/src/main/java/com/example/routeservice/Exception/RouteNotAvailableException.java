package com.example.routeservice.Exception;

public class RouteNotAvailableException extends RuntimeException{
    public RouteNotAvailableException(String msg){
        super(msg);
    }
}
