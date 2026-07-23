package com.example.routeservice.controller;

import com.example.routeservice.dto.RequestDTO;
import com.example.routeservice.dto.ResponseDTO;
import com.example.routeservice.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/routes")
    public class RouteController {

        @Autowired
        private RouteService service;

        @PostMapping("/create")
        public ResponseEntity<ResponseDTO> registerRoute(
                @Valid @RequestBody RequestDTO dto) {

            return ResponseEntity.ok(service.registerRoute(dto));
        }

        @GetMapping("/{id}")
        public ResponseEntity<ResponseDTO> getRouteById(@PathVariable Long id) {
            return ResponseEntity.ok(service.getRouteById(id));
        }

        @GetMapping
        public ResponseEntity<List<ResponseDTO>> getAllRoutes() {
            return ResponseEntity.ok(service.getAllRoutes());
        }

        @PutMapping("/{id}")
        public ResponseEntity<ResponseDTO> updateRoute(@PathVariable Long id, @Valid @RequestBody RequestDTO dto) {
            return ResponseEntity.ok(service.updateRouteById(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
            service.deleteRouteById(id);
            return ResponseEntity.ok("Route deleted successfully.");
        }

        @GetMapping("/search")
        public ResponseEntity<ResponseDTO> findBestRoute(@RequestParam String start, @RequestParam String end) {
            return ResponseEntity.ok(service.findBestRoute(start, end));
        }

    }

