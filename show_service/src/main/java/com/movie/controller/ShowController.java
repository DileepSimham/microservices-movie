package com.movie.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.client.MovieClient;
import com.movie.entity.Show;
import com.movie.responseDTO.MovieDTO;
import com.movie.responseDTO.ShowResponseDTO;
import com.movie.service.ShowService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/shows")
@Slf4j
public class ShowController {

	@Autowired
    private  ShowService showService;
    

	@Autowired
    private  MovieClient movieClient;



    @GetMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    public ShowResponseDTO getShowWithMovie(@PathVariable Long id) {
        log.info("Fetching show with ID: {}", id);
        Show show = showService.getShowById(id);

        log.info("Calling Movie Service for movieId: {}", show.getMovieId());
        MovieDTO movie = movieClient.getMovieById(show.getMovieId());

        ShowResponseDTO response = new ShowResponseDTO(show, movie);
        return response;
    }

    @GetMapping("/movie/{movieId}")
    public List<Show> getShowsByMovieId(@PathVariable Long movieId) {
        return showService.getShowsByMovieId(movieId);
    }

    @PostMapping
    public Show createShow(@RequestBody Show show) {
    	log.info("payload recieved: {}",show);
        return showService.createShow(show);
    }

    @DeleteMapping("/{id}")
    public void deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
    }
}
