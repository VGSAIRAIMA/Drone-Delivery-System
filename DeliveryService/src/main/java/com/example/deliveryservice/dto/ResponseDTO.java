package com.example.deliveryservice.dto;

import com.example.deliveryservice.entity.DeliveryStatus;
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
    private PackageResponseDTO packageDetails;
    private RouteResponseDTO routeDetails;
    private DroneResponseDTO droneDetails;
    private Double distance;
    private DeliveryStatus status;
}
