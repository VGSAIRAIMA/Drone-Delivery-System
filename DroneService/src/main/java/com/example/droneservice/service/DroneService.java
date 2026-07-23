package com.example.droneservice.service;

import com.example.droneservice.dto.RequestDTO;
import com.example.droneservice.dto.ResponseDTO;
import com.example.droneservice.dto.StatusUpdateDTO;
import com.example.droneservice.entity.DroneEntity;
import com.example.droneservice.entity.DroneStatus;
import com.example.droneservice.exception.DroneNotAvailableException;
import com.example.droneservice.exception.DroneNotFoundException;
import com.example.droneservice.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DroneService {

    private static final Logger logger = LoggerFactory.getLogger(DroneService.class);

    @Autowired
    public DroneRepository repository;

    public ResponseDTO createDrone(RequestDTO dto) {
        logger.info("Registering drone {}", dto.getDroneCode());

        DroneEntity entity = new DroneEntity();
        entity.setDroneCode(dto.getDroneCode());
        entity.setBatteryPercentage(dto.getBatteryPercentage());
        entity.setMaxPayload(dto.getMaxPayload());
        entity.setMaxDistance(dto.getMaxDistance());
        entity.setStatus(DroneStatus.AVAILABLE);

        DroneEntity saved = repository.save(entity);

        logger.info("Drone registered successfully with id {}", saved.getId());

        return mapToResponse(saved);
    }

    public ResponseDTO getDroneById(Long id) {
        logger.info("Fetching drone with id {}", id);

        DroneEntity drone = repository.findById(id).orElseThrow(() -> {
            logger.error("Drone not found with id {}", id);
            return new DroneNotFoundException("Drone not found with id : " + id);
        });

        logger.info("Drone found successfully");

        return mapToResponse(drone);
    }

    public List<ResponseDTO> getAllDrones() {
        logger.info("Fetching all drones");

        List<DroneEntity> drones = repository.findAll();
        List<ResponseDTO> response = new ArrayList<>();

        for (DroneEntity drone : drones) {
            response.add(mapToResponse(drone));
        }

        logger.info("Total drones found {}", response.size());

        return response;
    }

    public ResponseDTO updateDroneStatus(Long id, StatusUpdateDTO dto) {
        logger.info("Updating status of drone {}", id);

        DroneEntity drone = repository.findById(id).orElseThrow(() -> {
            logger.error("Drone not found with id {}", id);
            return new DroneNotFoundException("Drone not found with id : " + id);
        });

        drone.setStatus(dto.getStatus());

        DroneEntity saved = repository.save(drone);

        logger.info("Drone {} status updated to {}", id, dto.getStatus());

        return mapToResponse(saved);
    }

    public void deleteDrone(Long id) {
        logger.info("Deleting drone {}", id);

        DroneEntity drone = repository.findById(id).orElseThrow(() -> {
            logger.error("Drone not found with id {}", id);
            return new DroneNotFoundException("Drone not found with id : " + id);
        });

        repository.delete(drone);

        logger.info("Drone deleted successfully");
    }

    public ResponseDTO findAvailableDrone(double weight, double distance) {
        logger.info("Searching available drone for weight {} kg and distance {} km", weight, distance);

        List<DroneEntity> drones = repository.findAll();

        for (DroneEntity drone : drones) {
            if (drone.getStatus() == DroneStatus.AVAILABLE &&
                    drone.getBatteryPercentage() >= 30 &&
                    drone.getMaxPayload() >= weight &&
                    drone.getMaxDistance() >= distance) {

                logger.info("Suitable drone found: {}", drone.getDroneCode());

                return mapToResponse(drone);
            }
        }

        logger.warn("No drone available for weight {} kg and distance {} km", weight, distance);

        throw new DroneNotAvailableException("No drone available for given weight and distance.");
    }

    private ResponseDTO mapToResponse(DroneEntity drone) {
        ResponseDTO response = new ResponseDTO();
        response.setId(drone.getId());
        response.setDroneCode(drone.getDroneCode());
        response.setBatteryPercentage(drone.getBatteryPercentage());
        response.setMaxPayload(drone.getMaxPayload());
        response.setMaxDistance(drone.getMaxDistance());
        response.setStatus(drone.getStatus());
        return response;
    }
}

/*New method: public ResponseDTO findAvailableDrone(double weight, double distance) {
    return repository.findAll().stream()
            .filter(drone -> drone.getStatus() == DroneStatus.AVAILABLE)
            .filter(drone -> drone.getBatteryPercentage() >= 30)
            .filter(drone -> drone.getMaxPayload() >= weight)
            .filter(drone -> drone.getMaxDistance() >= distance)
            .findFirst()
            .map(this::mapToResponse)
            .orElseThrow(() -> new DroneNotAvailableException("No drone available for given weight and distance."));
}
JAVA STREAMS METHOD!!! */