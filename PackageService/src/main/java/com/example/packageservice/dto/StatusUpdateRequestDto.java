package com.example.packageservice.dto;

import com.example.packageservice.entity.PackageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdateRequestDto {
    private PackageStatus status;
}
