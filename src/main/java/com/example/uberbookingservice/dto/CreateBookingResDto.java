package com.example.uberbookingservice.dto;

import lombok.*;

import java.sql.Driver;
import java.util.Optional;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingResDto {

    private Long bookingId ;

    private String bookingStatus ;

    private Optional<Driver> driver ;
}
