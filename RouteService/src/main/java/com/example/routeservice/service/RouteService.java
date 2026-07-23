package com.example.routeservice.service;

import com.example.routeservice.Exception.RouteNotAvailableException;
import com.example.routeservice.Exception.RouteNotFoundException;
import com.example.routeservice.dto.RequestDTO;
import com.example.routeservice.dto.ResponseDTO;
import com.example.routeservice.entity.RouteEntity;
import com.example.routeservice.entity.TrafficStatus;
import com.example.routeservice.entity.WeatherStatus;
import com.example.routeservice.repository.RouteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private static final Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private RouteRepository repository;

    public ResponseDTO registerRoute(RequestDTO dto) {
        logger.info("Registering route from {} to {}", dto.getStartLocation(), dto.getEndLocation());

        RouteEntity route = new RouteEntity();
        route.setStartLocation(dto.getStartLocation());
        route.setEndLocation(dto.getEndLocation());
        route.setDistance(dto.getDistance());
        route.setWeatherStatus(dto.getWeatherStatus());
        route.setTrafficStatus(dto.getTrafficStatus());

        RouteEntity saved = repository.save(route);

        logger.info("Route registered successfully with id {}", saved.getId());

        return mapToResponse(saved);
    }

    public ResponseDTO getRouteById(Long id) {
        logger.info("Fetching route with id {}", id);

        RouteEntity route = repository.findById(id).orElseThrow(() -> {
            logger.error("Route not found with id {}", id);
            return new RouteNotFoundException("Route not found with id : " + id);
        });

        logger.info("Route found successfully");

        return mapToResponse(route);
    }

    public List<ResponseDTO> getAllRoutes() {
        logger.info("Fetching all routes");

        List<RouteEntity> routes = repository.findAll();
        List<ResponseDTO> response = new ArrayList<>();

        for (RouteEntity route : routes) {
            response.add(mapToResponse(route));
        }

        logger.info("Total routes found {}", response.size());

        return response;
    }

    public ResponseDTO updateRouteById(Long id, RequestDTO dto) {
        logger.info("Updating route with id {}", id);

        RouteEntity route = repository.findById(id).orElseThrow(() -> {
            logger.error("Route not found with id {}", id);
            return new RouteNotFoundException("Route not found with id : " + id);
        });

        route.setStartLocation(dto.getStartLocation());
        route.setEndLocation(dto.getEndLocation());
        route.setDistance(dto.getDistance());
        route.setWeatherStatus(dto.getWeatherStatus());
        route.setTrafficStatus(dto.getTrafficStatus());

        RouteEntity saved = repository.save(route);

        logger.info("Route updated successfully");

        return mapToResponse(saved);
    }

    public void deleteRouteById(Long id) {
        logger.info("Deleting route with id {}", id);

        RouteEntity route = repository.findById(id).orElseThrow(() -> {
            logger.error("Route not found with id {}", id);
            return new RouteNotFoundException("Route not found with id : " + id);
        });

        repository.delete(route);

        logger.info("Route deleted successfully");
    }

    public ResponseDTO findBestRoute(String start, String end) {
        logger.info("Searching best route from {} to {}", start, end);

        List<RouteEntity> routes = repository.findByStartLocationIgnoreCaseAndEndLocationIgnoreCase(start, end);
        RouteEntity bestRoute = null;

        for (RouteEntity route : routes) {
            if (route.getWeatherStatus() == WeatherStatus.CLEAR &&
                    (route.getTrafficStatus() == TrafficStatus.LOW ||
                            route.getTrafficStatus() == TrafficStatus.MEDIUM)) {

                if (bestRoute == null) {
                    bestRoute = route;
                } else if (bestRoute.getDistance() > route.getDistance()) {
                    bestRoute = route;
                }
            }
        }

        if (bestRoute == null) {
            logger.warn("No valid route available between {} and {}", start, end);
            throw new RouteNotAvailableException("No valid route found between " + start + " and " + end + ". All available routes were blocked by weather or traffic conditions.");
        }

        logger.info("Best route found with distance {} km", bestRoute.getDistance());

        return mapToResponse(bestRoute);
    }

    private ResponseDTO mapToResponse(RouteEntity route) {
        ResponseDTO response = new ResponseDTO();

        response.setId(route.getId());
        response.setStartLocation(route.getStartLocation());
        response.setEndLocation(route.getEndLocation());
        response.setDistance(route.getDistance());
        response.setWeatherStatus(route.getWeatherStatus());
        response.setTrafficStatus(route.getTrafficStatus());

        return response;
    }
}