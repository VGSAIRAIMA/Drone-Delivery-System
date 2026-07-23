package com.example.routeservice.dto;

import com.example.routeservice.entity.TrafficStatus;
import com.example.routeservice.entity.WeatherStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDTO {


        @NotBlank(message = "Sender Location cannot be empty")
        private String startLocation;

        @NotBlank(message= "Receiver Location cannot be empty")
        private String endLocation;

        @Positive(message="Distance cannot be negative")
        private Double distance;

        @NotNull(message = "Weather Status cannot be null")
        private WeatherStatus weatherStatus;

        @NotNull(message = "Traffic Status cannot be null")
        private TrafficStatus trafficStatus;

}

