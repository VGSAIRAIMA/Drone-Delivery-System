package com.example.droneservice.controller;

import com.example.droneservice.dto.RequestDTO;
import com.example.droneservice.dto.ResponseDTO;
import com.example.droneservice.dto.StatusUpdateDTO;
import com.example.droneservice.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/drones")
public class DroneController {

    @Autowired
    private DroneService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> registerDrone(@Valid @RequestBody RequestDTO dto) {
        return ResponseEntity.ok(service.createDrone(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getDroneById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDroneById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getAllDrones() {
        return ResponseEntity.ok(service.getAllDrones());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseDTO> updateDroneStatus (@PathVariable Long id, @RequestBody StatusUpdateDTO dto) {
        return ResponseEntity.ok(service.updateDroneStatus(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDrone(@PathVariable Long id) {
        service.deleteDrone(id);
        return ResponseEntity.ok("Drone deleted successfully.");
    }

    @GetMapping("/available")
    public ResponseEntity<ResponseDTO> findAvailableDrone(@RequestParam double weight, @RequestParam double distance) {
        return ResponseEntity.ok(
                service.findAvailableDrone(weight, distance));
    }

}
