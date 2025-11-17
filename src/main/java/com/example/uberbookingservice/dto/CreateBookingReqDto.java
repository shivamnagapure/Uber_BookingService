package com.example.uberbookingservice.dto;

import com.example.uberprojectentityservice.models.ExactLocation;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingReqDto {

    private Long passengerId ;

    private ExactLocation startLocation ;

    private ExactLocation endLocation ;

}
