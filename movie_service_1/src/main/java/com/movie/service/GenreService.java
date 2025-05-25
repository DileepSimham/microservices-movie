package com.movie.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movie.dto.GenreDTO;
import com.movie.dto.GenreResponse;
import com.movie.entity.Genre;
import com.movie.repository.GenreRepository;
import com.movie.util.MovieServiceConstants;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Page<GenreDTO> getAllAvailablesGenres(Pageable pageable) {
		log.info("Fetching all genres from local database with pagination: page={}, size={}", pageable.getPageNumber(),
				pageable.getPageSize());

		Page<Genre> genrePage = genreRepository.findAll(pageable);

		if (genrePage.isEmpty()) {
			log.info("No genres in DB, calling external API");
			List<GenreDTO> externalGenres = getAllAvailablesGenresExternal();

			if (!externalGenres.isEmpty()) {
				externalGenres.stream().map(dto -> Genre.builder().genreName(dto.getGenreName())
						.officialGenreId(dto.getOfficialGenreId()).build()).forEach(saved -> {
							genreRepository.save(saved);
							log.debug("Saved genre: {}", saved);
						});

				genrePage = genreRepository.findAll(pageable); // re-fetch with pagination
			}
		}

		return genrePage.map(genre -> new GenreDTO(genre.getGenreName(), genre.getOfficialGenreId()));
	}

	public List<GenreDTO> getAllAvailablesGenresExternal() {
		try {
			log.info("Starting to fetch genres from TMDb API");

			String url = "https://api.themoviedb.org/3/genre/movie/list?language=en";

			HttpHeaders headers = new HttpHeaders();
			headers.set("accept", "application/json");
			headers.set("Authorization", "Bearer " + MovieServiceConstants.TMDB_ACCESS_TOKEN);

			HttpEntity<String> entity = new HttpEntity<>(headers);

			ResponseEntity<GenreResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					GenreResponse.class);

			log.info("Successfully fetched genres from TMDb API, total genres: {}",
					response.getBody().getGenres().size());

			return response.getBody().getGenres().stream().map(i -> new GenreDTO(i.getName(), i.getId()))
					.collect(Collectors.toList());

		} catch (Exception e) {
			log.error("Failed to fetch genres from TMDb API", e);
			return Collections.emptyList();
		}
	}

}
