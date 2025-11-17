package com.example.uberbookingservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingUpdateResponseDto {
    private Long bookingId;
    private String bookingStatus;
    private Long driverId;
}
