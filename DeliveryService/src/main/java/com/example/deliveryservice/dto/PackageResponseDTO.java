package com.example.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageResponseDTO {
    private Long id;
    private String senderLocation;
    private String receiverLocation;
    private String product;
    private Double totalWeight;
    /*developers avoid storing data that can always be derived from other fields.*/
}
