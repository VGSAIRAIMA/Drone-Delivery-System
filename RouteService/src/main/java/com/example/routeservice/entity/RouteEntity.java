package com.example.routeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String startLocation;

    private String endLocation;

    private Double distance;

    @Enumerated(EnumType.STRING)
    private WeatherStatus weatherStatus;

    @Enumerated(EnumType.STRING)
    private TrafficStatus trafficStatus;
}
