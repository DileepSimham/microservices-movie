package com.movie.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.dto.MovieDTO;
import com.movie.jdbc.JdbcExecuteMethodsService;

import lombok.extern.slf4j.Slf4j;

//This is a REST controller that handles HTTP requests and returns responses in JSON format.
//It combines @Controller and @ResponseBody, so no need to annotate each method with @ResponseBody.

//@ResponseBody indicates that the return value of this method will be sent directly to the HTTP response body, not as a view.
//It converts the method’s return value (like an object) to JSON (or other formats) and sends it to the client.
//In @RestController, @ResponseBody is applied automatically to all methods.

@RestController

//Base URL path for all endpoints in this controller will start with /movies/jdbc
//For example, @GetMapping("/insertMovie") becomes /movies/jdbc/insertMovie
@RequestMapping("/movies/jdbc")
@Slf4j
public class JdbcController {

	private static final Logger log = LoggerFactory.getLogger(JdbcController.class);
	@Autowired
	private JdbcExecuteMethodsService jdbcService;

	@PostMapping("/insertMovie")
	public ResponseEntity<String> insertMovies() {

		log.info("[START] /insertMovie endpoint hit - initiating insert operation");

		Integer insertMovies = jdbcService.insertMovies();

		if (insertMovies > 0) {
			log.info("Successfully inserted {} movie record(s) into the database", insertMovies);
		} else {
			log.warn("No movie records were inserted. Possible failure in the insert operation.");
		}

		log.info("[END] /insertMovie - returning response to client");
		return ResponseEntity.ok(insertMovies + " row(s) inserted");
	}

	@GetMapping("/getMovies")
	public ResponseEntity<List<MovieDTO>> getMovies() {
		// Fetch movies using the service
		List<MovieDTO> fetchMovies = jdbcService.fetchMovies();

		// Log whether the fetch operation was successful or no records were found
		if (!fetchMovies.isEmpty()) {
			log.info("Successfully fetched {} movie record(s) from the database", fetchMovies.size());
		} else {
			log.warn("No movie records were found. Possible failure in the fetch operation.");
		}

		// Log the end of the method
		log.info("[END] /getMovies - returning response to client");

		// Return the list of movies in the response
		return ResponseEntity.ok(fetchMovies);
	}

}
