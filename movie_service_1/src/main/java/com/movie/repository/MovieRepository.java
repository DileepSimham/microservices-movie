package com.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
	List<Movie> findByGenre(String genre);

	List<Movie> findByLanguage(String language);
}
