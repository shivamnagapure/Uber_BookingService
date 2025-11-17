package com.example.uberbookingservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingUpdateRequest {
    private String bookingStatus;
    private Long driverId;
}
