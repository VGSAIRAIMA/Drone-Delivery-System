package com.example.droneservice.repository;

import com.example.droneservice.entity.DroneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneRepository extends JpaRepository<DroneEntity,Long> {
}
