
package com.example.deliveryservice.controller;

import com.example.deliveryservice.dto.RequestDTO;
import com.example.deliveryservice.dto.ResponseDTO;
import com.example.deliveryservice.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> assignDelivery(@Valid @RequestBody RequestDTO dto) {
        return ResponseEntity.ok(service.assignDelivery(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getDeliveryById(
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getDeliveryById(id));
    }

    @GetMapping
    public ResponseEntity<List<ResponseDTO>> getAllDeliveries() {
        return ResponseEntity.ok(service.getAllDeliveries());
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<ResponseDTO> startDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(service.startDelivery(id));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<ResponseDTO> completeDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(service.completeDelivery(id));
    }
}