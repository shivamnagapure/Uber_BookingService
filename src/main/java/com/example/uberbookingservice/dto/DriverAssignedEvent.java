package com.example.uberbookingservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverAssignedEvent {
    private String bookingId;
    private String passengerId;
    private String driverId;
}

