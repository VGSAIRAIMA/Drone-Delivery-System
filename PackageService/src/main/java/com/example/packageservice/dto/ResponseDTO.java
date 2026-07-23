package com.example.packageservice.dto;

import com.example.packageservice.entity.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {
    private Long id;
    private String senderLocation;
    private String receiverLocation;
    private String product;
    private Double totalWeight; /*developers avoid storing data that can always be derived from other fields.*/
    private Integer qty;
    private PackageStatus status;
}
