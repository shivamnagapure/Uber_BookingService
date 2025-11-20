package com.example.uberbookingservice.services;

import com.example.uberbookingservice.dto.BookingUpdateRequest;
import com.example.uberbookingservice.dto.BookingUpdateResponseDto;
import com.example.uberbookingservice.dto.CreateBookingReqDto;
import com.example.uberbookingservice.dto.CreateBookingResDto;
import com.example.uberprojectentityservice.models.Booking;
import reactor.core.publisher.Mono;

public interface BookingService {

    Mono<CreateBookingResDto> createBooking(CreateBookingReqDto bookingDetails) ;

    BookingUpdateResponseDto updateBooking(Long bookingId , BookingUpdateRequest updateRequest);
}
