package com.example.droneservice.entity;

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
public class DroneEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String droneCode;
    private Integer batteryPercentage;
    private Double maxPayload;
    private Double maxDistance;
    @Enumerated(EnumType.STRING)
    private DroneStatus status;
}
