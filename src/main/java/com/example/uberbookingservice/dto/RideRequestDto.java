package com.example.uberbookingservice.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {
    private String bookingId;
    private String passengerId ;
    private double pickupLat;
    private double pickupLng;
    private List<DriverLocationDto> nearbyDrivers;
}
