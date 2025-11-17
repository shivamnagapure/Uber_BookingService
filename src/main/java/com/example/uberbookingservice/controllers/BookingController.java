package com.example.uberbookingservice.controllers;

import com.example.uberbookingservice.dto.BookingUpdateRequest;
import com.example.uberbookingservice.dto.BookingUpdateResponseDto;
import com.example.uberbookingservice.dto.CreateBookingReqDto;
import com.example.uberbookingservice.dto.CreateBookingResDto;
import com.example.uberbookingservice.services.BookingService;
import com.example.uberprojectentityservice.models.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/va/booking")
public class BookingController {

    BookingService bookingService ;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService ;
    }

    @PostMapping
    public ResponseEntity<CreateBookingResDto> createBooking(@RequestBody CreateBookingReqDto createBookingReqDto){
        System.out.println(createBookingReqDto.getPassengerId());
        CreateBookingResDto resDto = bookingService.createBooking(createBookingReqDto) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingUpdateResponseDto> updateBooking(@PathVariable("bookingId") Long bookingId,
                                                                  @RequestBody BookingUpdateRequest request){
        BookingUpdateResponseDto updatedBooking  = bookingService.updateBooking(bookingId , request);
        return ResponseEntity.ok(updatedBooking);
    }

}
