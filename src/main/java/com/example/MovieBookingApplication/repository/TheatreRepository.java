package com.example.MovieBookingApplication.repository;

import com.example.MovieBookingApplication.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    Optional<List<Theatre>> findByLocation(String location);
}
