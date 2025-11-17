package com.example.uberbookingservice.repositories;

import com.example.uberprojectentityservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger , Long> {
    Optional<Passenger> findPassengerById(Long id);
}
