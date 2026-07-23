package com.example.packageservice.service;

import com.example.packageservice.dto.RequestDTO;
import com.example.packageservice.dto.ResponseDTO;
import com.example.packageservice.dto.StatusUpdateRequestDto;
import com.example.packageservice.entity.PackageEntity;
import com.example.packageservice.entity.PackageStatus;
import com.example.packageservice.exception.PackageNotFoundException;
import com.example.packageservice.repository.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageServiceLayer {

    private static final Logger logger = LoggerFactory.getLogger(PackageServiceLayer.class);

    @Autowired
    private PackageRepository repository;

    public ResponseDTO createPackage(RequestDTO dto){
        logger.info("Creating package from {} to {}", dto.getSenderLocation(), dto.getReceiverLocation());

        PackageEntity obj = new PackageEntity();
        obj.setSenderLocation(dto.getSenderLocation());
        obj.setReceiverLocation(dto.getReceiverLocation());
        obj.setProduct(dto.getProduct());
        obj.setTotalWeight(dto.getTotalWeight());
        obj.setQty(dto.getQty());
        obj.setStatus(PackageStatus.CREATED);

        PackageEntity saved = repository.save(obj);

        logger.info("Package created successfully with id {}", saved.getId());

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setSenderLocation(saved.getSenderLocation());
        response.setReceiverLocation(saved.getReceiverLocation());
        response.setProduct(saved.getProduct());
        response.setTotalWeight(saved.getTotalWeight());
        response.setQty(saved.getQty());
        response.setStatus(saved.getStatus());
        return response;
    }

    public ResponseDTO getPackageById(Long id){
        logger.info("Fetching package with id {}", id);

        PackageEntity saved = repository.findById(id).orElseThrow(() -> {
            logger.error("Package not found with id {}", id);
            return new PackageNotFoundException("Required Package is not found!");
        });

        logger.info("Package fetched successfully");

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setSenderLocation(saved.getSenderLocation());
        response.setReceiverLocation(saved.getReceiverLocation());
        response.setProduct(saved.getProduct());
        response.setTotalWeight(saved.getTotalWeight());
        response.setQty(saved.getQty());
        response.setStatus(saved.getStatus());
        return response;
    }

    public ResponseDTO updatePackageStatusById(Long id, StatusUpdateRequestDto dto){
        logger.info("Updating package {} status to {}", id, dto.getStatus());

        PackageEntity saved = repository.findById(id).orElseThrow(() -> {
            logger.error("Package not found with id {}", id);
            return new PackageNotFoundException("Required Package is not found!");
        });

        saved.setStatus(dto.getStatus());
        repository.save(saved);

        logger.info("Package status updated successfully");

        ResponseDTO response = new ResponseDTO();
        response.setId(saved.getId());
        response.setSenderLocation(saved.getSenderLocation());
        response.setReceiverLocation(saved.getReceiverLocation());
        response.setProduct(saved.getProduct());
        response.setTotalWeight(saved.getTotalWeight());
        response.setQty(saved.getQty());
        response.setStatus(dto.getStatus());
        return response;
    }
}