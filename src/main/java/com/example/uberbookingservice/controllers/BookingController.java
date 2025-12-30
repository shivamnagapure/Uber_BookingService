package com.example.uberbookingservice.controllers;

import com.example.uberbookingservice.dto.*;
import com.example.uberbookingservice.services.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/va/booking")
public class BookingController {

    BookingService bookingService ;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService ;
    }

    @PostMapping
    public Mono<ResponseEntity<CreateBookingResDto>> createBooking(@RequestBody CreateBookingReqDto createBookingReqDto){
        System.out.println(createBookingReqDto.getPassengerId());

        return bookingService.createBooking(createBookingReqDto)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingUpdateResponseDto> updateBooking(@PathVariable("bookingId") Long bookingId,
                                                                  @RequestBody BookingUpdateRequest request){
        BookingUpdateResponseDto updatedBooking  = bookingService.updateBooking(bookingId , request);
        return ResponseEntity.ok(updatedBooking);
    }

    @PostMapping("/driver-response")
    public ResponseEntity<DriverResponseDto> handleDriverResponse(@RequestBody DriverResponseDto response){
        bookingService.updateDriverAssignmentAndNotify(response.getBookingId() , response.getDriverId());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
