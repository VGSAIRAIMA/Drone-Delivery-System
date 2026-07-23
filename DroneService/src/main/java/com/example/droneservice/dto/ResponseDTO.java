package com.example.droneservice.dto;

import com.example.droneservice.entity.DroneStatus;
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
    private String droneCode;
    private Integer batteryPercentage;
    private Double maxPayload;
    private Double maxDistance;
    private DroneStatus status;
}
