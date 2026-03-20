package com.example.MovieBookingApplication.repository;

import com.example.MovieBookingApplication.entity.Booking;
import com.example.MovieBookingApplication.entity.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);
    List<Booking> findByShowId(Long showId);
    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
