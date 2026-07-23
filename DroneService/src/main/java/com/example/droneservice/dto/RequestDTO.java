package com.example.droneservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDTO {
    @NotBlank(message = "Drone Code cannot be blank")
    private String droneCode;
    @Min(value=0,message="Battery Percentage cannot be nagative! ")
    @Max(value=100,message = "Battery percentage cannot be greater than 100!")
    private Integer batteryPercentage;
    @Positive(message = "Maximum payload must be greater than 0")
    private Double maxPayload;

    @Positive(message = "Maximum distance must be greater than 0")
    private Double maxDistance;

}
