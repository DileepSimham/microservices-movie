package com.movie.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.movie.responseDTO.MovieDTO;

@FeignClient(name = "movieservice")
public interface MovieClient {

    @GetMapping("/movies/{id}")
    MovieDTO getMovieById(@PathVariable("id") Long id);
}
