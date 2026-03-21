package com.example.MovieBookingApplication.service;

import com.example.MovieBookingApplication.dto.ShowDTO;
import com.example.MovieBookingApplication.entity.Booking;
import com.example.MovieBookingApplication.entity.Movie;
import com.example.MovieBookingApplication.entity.Show;
import com.example.MovieBookingApplication.entity.Theatre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.MovieBookingApplication.repository.MovieRepository;
import com.example.MovieBookingApplication.repository.ShowRepository;
import com.example.MovieBookingApplication.repository.TheatreRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    public Show createShow(ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No Movie Found for id: " + showDTO.getMovieId()));

        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(() -> new RuntimeException("No Theatre Found for id: "+ showDTO.getTheatreId()));

        Show show = new Show();
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheatre(theatre);

        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(Long movieId) {
        Optional<List<Show>> showListBox = showRepository.findByMovieId(movieId);

        if(showListBox.isPresent()) {
            return showListBox.get();
        } else {
            throw new RuntimeException("No shows available for the movie");
        }
    }

    public List<Show> getShowsByTheatre(Long theatreId) {
        Optional<List<Show>> showListBox = showRepository.findByTheatreId(theatreId);

        if(showListBox.isPresent()) {
            return showListBox.get();
        } else {
            throw new RuntimeException("No shows available for the theatre");
        }
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No show available for the id: " + id));

        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("No Movie Found for id: " + showDTO.getMovieId()));

        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(() -> new RuntimeException("No Theatre Found for id: "+ showDTO.getTheatreId()));

        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        show.setMovie(movie);
        show.setTheatre(theatre);

        return showRepository.save(show);
    }

    public void deleteShow(Long id) {

        if(!showRepository.existsById(id)) {
            throw new RuntimeException("No show available for the id: " + id);
        }

        List<Booking> bookings = showRepository.findById(id).get().getBookings();

        if(!bookings.isEmpty()) {
            throw new RuntimeException("Can't delete show with existing bookings");
        }

        showRepository.deleteById(id);
    }
}
