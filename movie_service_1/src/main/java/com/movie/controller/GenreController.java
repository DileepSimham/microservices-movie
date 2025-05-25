package com.movie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.movie.dto.GenreDTO;
import com.movie.service.GenreService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/genres")
@Slf4j
public class GenreController {

	@Autowired
	private GenreService genreService;

	@GetMapping("/internal")
	public ResponseEntity<Page<GenreDTO>> getAllAvailablesGenres(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {

		log.info("Inside internal API - fetching genres with pagination, page={}, size={}", page, size);

		Pageable pageable = PageRequest.of(page, size);
		Page<GenreDTO> pagedGenres = genreService.getAllAvailablesGenres(pageable);

		return ResponseEntity.ok(pagedGenres);
	}

	@GetMapping("/external")
	public ResponseEntity<List<GenreDTO>> getAllAvailablesGenresExternal() {
		log.info("Received request to fetch all available external genres");
		List<GenreDTO> allAvailablesGenresExternal = genreService.getAllAvailablesGenresExternal();
		log.info("Returning {} genres to the client", allAvailablesGenresExternal.size());
		return ResponseEntity.ok(allAvailablesGenresExternal);
	}
}
