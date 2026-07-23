package com.example.packageservice.controller;

import com.example.packageservice.dto.RequestDTO;
import com.example.packageservice.dto.ResponseDTO;
import com.example.packageservice.dto.StatusUpdateRequestDto;
import com.example.packageservice.service.PackageServiceLayer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/packages")
public class PackageController {
    private PackageServiceLayer service;
    public PackageController(PackageServiceLayer service){
        this.service = service;
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createPackage(@Valid @RequestBody RequestDTO dto){
        return ResponseEntity.ok(service.createPackage(dto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getPackageById(@PathVariable Long id){
        return ResponseEntity.ok(service.getPackageById(id));
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ResponseDTO> updatePackageStatusById(@PathVariable Long id,@RequestBody StatusUpdateRequestDto dto){
        return ResponseEntity.ok(service.updatePackageStatusById(id,dto));
    }
}
