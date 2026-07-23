package com.example.deliveryservice.service;
import com.example.deliveryservice.dto.*;
import com.example.deliveryservice.entity.DeliveryEntity;
import com.example.deliveryservice.entity.DeliveryStatus;
import com.example.deliveryservice.exception.*;
import com.example.deliveryservice.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    @Autowired
    private DeliveryRepository repository;


    @Autowired
    private RestTemplate restTemplate;

    public ResponseDTO assignDelivery(RequestDTO dto){
        logger.info("Assigning delivery for package id {}", dto.getPackageId());
        PackageResponseDTO packages;
        try{
            logger.info("Calling Package Service...");
         packages = restTemplate.getForObject(
                "http://localhost:8080/packages/"+dto.getPackageId(),
                PackageResponseDTO.class);
        }
        catch(HttpClientErrorException ex){
            logger.error("Package not available");
            ErrorResponse error = ex.getResponseBodyAs(ErrorResponse.class);
            throw new PackageNotAvailableException(error.getErrorMessage());

        }
        String uri = UriComponentsBuilder.fromUriString("http://localhost:8082/routes/search")
                .queryParam("start",packages.getSenderLocation())
                .queryParam("end",packages.getReceiverLocation())
                .toUriString();
        RouteResponseDTO route;
        try{
            logger.info("Calling Route Service...");
            route = restTemplate.getForObject(uri,RouteResponseDTO.class);
        }
        catch(HttpClientErrorException ex){
            logger.error("Route not available");
            ErrorResponse error = ex.getResponseBodyAs(ErrorResponse.class);
            throw new RouteNotAvailableException(error.getErrorMessage());
        }

        String droneUri = UriComponentsBuilder.fromUriString("http://localhost:8081/drones/available")
                .queryParam("weight",packages.getTotalWeight())
                .queryParam("distance",route.getDistance())
                .toUriString();
        DroneResponseDTO drone;
        try{
            logger.info("Calling Drone Service...");
            drone = restTemplate.getForObject(droneUri,DroneResponseDTO.class);
        }
        catch(HttpClientErrorException ex){
            logger.error("Drone not available");
            ErrorResponse error = ex.getResponseBodyAs(ErrorResponse.class);
            throw new DroneNotAvailableException(error.getErrorMessage());
        }

        DeliveryEntity delivery=new DeliveryEntity();
        delivery.setPackageId(packages.getId());
        delivery.setDroneId(drone.getId());
        delivery.setDroneCode(drone.getDroneCode());
        delivery.setDistance(route.getDistance());
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        logger.info("Assigning drone {} to package {}", drone.getDroneCode(), packages.getId());
        DeliveryEntity saved = repository.save(delivery);
        logger.info("Delivery created successfully with id {}", saved.getDeliveryId());

        StatusUpdateRequestDTO packageStatus = new StatusUpdateRequestDTO();
        packageStatus.setStatus("ASSIGNED");
        logger.info("Updating Package Service status to ASSIGNED");
        restTemplate.put("http://localhost:8080/packages/"+packages.getId()+"/status",packageStatus);

        StatusUpdateRequestDTO droneStatus = new StatusUpdateRequestDTO();
        droneStatus.setStatus("BUSY");
        logger.info("Updating Drone Service status to BUSY");
        restTemplate.put("http://localhost:8081/drones/"+drone.getId()+"/status",droneStatus);

        ResponseDTO response = new ResponseDTO();

        response.setId(saved.getDeliveryId());
        response.setDistance(saved.getDistance());
        response.setStatus(saved.getStatus());

        response.setPackageDetails(packages);
        response.setRouteDetails(route);
        response.setDroneDetails(drone);
        logger.info("Delivery assignment completed successfully");
        return response;

    }
    public ResponseDTO getDeliveryById(Long id) {
        logger.info("Fetching delivery with id {}", id);

        DeliveryEntity delivery = repository.findById(id)
                .orElseThrow(() ->
                        new DeliveryNotAvailableException("Delivery not found with id : " + id));
        logger.info("Delivery found");
        PackageResponseDTO packages = restTemplate.getForObject(
                "http://localhost:8080/packages/" + delivery.getPackageId(),
                PackageResponseDTO.class);

        DroneResponseDTO drone = restTemplate.getForObject(
                "http://localhost:8081/drones/" + delivery.getDroneId(),
                DroneResponseDTO.class);

        RouteResponseDTO route = new RouteResponseDTO();
        route.setDistance(delivery.getDistance());

        ResponseDTO response = new ResponseDTO();

        response.setId(delivery.getDeliveryId());
        response.setDistance(delivery.getDistance());
        response.setStatus(delivery.getStatus());

        response.setPackageDetails(packages);
        response.setDroneDetails(drone);
        response.setRouteDetails(route);
        logger.info("Returning delivery details");
        return response;
    }
    public List<ResponseDTO> getAllDeliveries() {
        logger.info("Fetching all deliveries");
        List<DeliveryEntity> deliveries = repository.findAll();

        List<ResponseDTO> responses = new ArrayList<>();

        for (DeliveryEntity delivery : deliveries) {

            responses.add(getDeliveryById(delivery.getDeliveryId()));
        }
        logger.info("Total deliveries found {}", responses.size());
        return responses;
    }
    public ResponseDTO startDelivery(Long id) {
        logger.info("Starting delivery {}", id);
        DeliveryEntity delivery =
                repository.findById(id)
                        .orElseThrow(() ->
                                new DeliveryNotAvailableException("Delivery not found"));

        delivery.setStatus(DeliveryStatus.IN_TRANSIT);

        repository.save(delivery);
        logger.info("Delivery {} status updated to IN_TRANSIT", id);
        StatusUpdateRequestDTO dto =
                new StatusUpdateRequestDTO();

        dto.setStatus("IN_TRANSIT");
        logger.info("Delivery {} status updated to IN_TRANSIT", id);
        logger.info("Updating Package Service status to IN_TRANSIT");
        restTemplate.put(
                "http://localhost:8080/packages/"+delivery.getPackageId()+"/status",dto);
        logger.info("Delivery {} started successfully", id);
        return getDeliveryById(id);
    }
    public ResponseDTO completeDelivery(Long id) {
        logger.info("Completing delivery {}", id);
        DeliveryEntity delivery =
                repository.findById(id)
                        .orElseThrow(() ->
                                new DeliveryNotAvailableException("Delivery not found"));

        delivery.setStatus(DeliveryStatus.DELIVERED);
        logger.info("Delivery {} marked as DELIVERED", id);
        repository.save(delivery);

        StatusUpdateRequestDTO packageDto =
                new StatusUpdateRequestDTO();

        packageDto.setStatus("DELIVERED");

        restTemplate.put(
                "http://localhost:8080/packages/"+delivery.getPackageId()+"/status",packageDto);

        StatusUpdateRequestDTO droneDto =
                new StatusUpdateRequestDTO();

        droneDto.setStatus("AVAILABLE");

        restTemplate.put(
                "http://localhost:8081/drones/"+ delivery.getDroneId()+"/status",droneDto);
        logger.info("Delivery {} completed successfully", id);
        return getDeliveryById(id);
    }
}
