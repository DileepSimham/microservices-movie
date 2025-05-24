package com.movie.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.client.MovieClient;
import com.movie.entity.Show;
import com.movie.repository.ShowRepository;

@Service
public class ShowService {

	@Autowired
	private final ShowRepository showRepository;
	
	 @Autowired
	 private MovieClient movieClient;

	public ShowService(ShowRepository showRepository) {
		this.showRepository = showRepository;
	}

	public List<Show> getAllShows() {
		return showRepository.findAll();
	}

	public Show getShowById(Long id) {
		return showRepository.findById(id).orElse(null);
	}

	public List<Show> getShowsByMovieId(Long movieId) {
		return showRepository.findByMovieId(movieId);
	}

	public Show createShow(Show show) {
		return showRepository.save(show);
	}

	public void deleteShow(Long id) {
		showRepository.deleteById(id);
	}
	
	
}
