package com.example.deliveryservice.repository;

import com.example.deliveryservice.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity,Long> {
}
