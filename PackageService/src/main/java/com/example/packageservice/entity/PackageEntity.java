package com.example.packageservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderLocation;
    private String receiverLocation;
    private String product;
    private Double totalWeight; /*developers avoid storing data that can always be derived from other fields.*/
    private Integer qty;
    @Enumerated(EnumType.STRING)
    private PackageStatus status;
}
