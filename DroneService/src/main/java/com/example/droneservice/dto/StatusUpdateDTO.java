package com.example.droneservice.dto;

import com.example.droneservice.entity.DroneStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateDTO {
    private DroneStatus status;
}
