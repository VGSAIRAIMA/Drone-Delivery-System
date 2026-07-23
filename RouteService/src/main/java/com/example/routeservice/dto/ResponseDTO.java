package com.example.routeservice.dto;

import com.example.routeservice.entity.TrafficStatus;
import com.example.routeservice.entity.WeatherStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private Long id;
    private String startLocation;
    private String endLocation;
    private Double distance;
    private WeatherStatus weatherStatus;
    private TrafficStatus trafficStatus;
}
