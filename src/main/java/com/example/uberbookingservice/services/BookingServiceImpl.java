package com.example.uberbookingservice.services;

import com.example.uberbookingservice.dto.*;
import com.example.uberbookingservice.repositories.BookingRepository;
import com.example.uberbookingservice.repositories.DriverRepository;
import com.example.uberbookingservice.repositories.PassengerRepository;
import com.example.uberprojectentityservice.models.Booking;
import com.example.uberprojectentityservice.models.BookingStatus;
import com.example.uberprojectentityservice.models.Driver;
import com.example.uberprojectentityservice.models.Passenger;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService{

    private final WebClient webClient;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository ;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              PassengerRepository passengerRepository,
                              DriverRepository driverRepository ,
                              @LoadBalanced WebClient.Builder webClientBuilder) {  // Inject Builder
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository ;
        this.webClient = webClientBuilder.build();  // Build WebClient instance
    }

    // make an api call to location service to fetch nearby drivers using web client
    public Mono<List<DriverLocationDto>> getNearByDrivers(NearbyDriverRequestDto request){
        System.out.println("GetNearByDrivers");
        return webClient.post()
                .uri("http://UBERPROJECT-LOCATIONSERVICE/api/location/nearby/drivers")
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(DriverLocationDto.class)
                .collectList();
    }


    @Override
    public CreateBookingResDto createBooking(CreateBookingReqDto bookingDetails) {
        System.out.println("In create Booking");
        Optional<Passenger> passenger = passengerRepository.findPassengerById(bookingDetails.getPassengerId());
        NearbyDriverRequestDto requestDto = NearbyDriverRequestDto.builder()
                .longitude(bookingDetails.getStartLocation().getLongitude())
                .latitude(bookingDetails.getStartLocation().getLatitude())
                .build();

        getNearByDrivers(requestDto)
                .subscribe(drivers -> System.out.println("All drivers: " + drivers));

        //TODO 1: solve duplication problem of location service
        Booking newBooking = Booking.builder()
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .startLocation(bookingDetails.getStartLocation()) // Hibernate thinks each ExactLocation object is new because it has no ID.
                .endLocation(bookingDetails.getEndLocation()) // And save it repeatedly
                .passenger(passenger.get())
                .build();

        Booking saveBooking = bookingRepository.save(newBooking);
        System.out.println("Booking Save");
        return CreateBookingResDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingStatus().toString())
                .build();

    }
    //Problem : convert input data-Type string to enum
    //Solution : valueOf(String) converts string into corresponding enum constant
    @Override
    public BookingUpdateResponseDto updateBooking(Long bookingId, BookingUpdateRequest updateRequest) {
        System.out.println(bookingId);
        Booking booking = bookingRepository.findBookingsById(bookingId);

        System.out.println(booking.getId());
        //update only if provided
        if(updateRequest.getBookingStatus() != null){
            booking.setBookingStatus(BookingStatus.valueOf(updateRequest.getBookingStatus()));
        }

        if(updateRequest.getDriverId() != null){
            Driver driver = driverRepository.findById(updateRequest.getDriverId())
                    .orElseThrow(() -> new RuntimeException("Driver not found with id " + updateRequest.getDriverId()));
            booking.setDriver(driver);
        }

        bookingRepository.save(booking);

        return BookingUpdateResponseDto.builder()
                .bookingId(booking.getId())
                .bookingStatus(booking.getBookingStatus().toString())
                .driverId(booking.getDriver().getId())
                .build();
    }
}
