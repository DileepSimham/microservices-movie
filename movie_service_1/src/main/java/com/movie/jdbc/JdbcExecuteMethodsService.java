package com.movie.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movie.dto.MovieDTO;
import com.movie.entity.Movie;
import com.movie.mapper.MovieMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JdbcExecuteMethodsService {

	private static final Logger log = LoggerFactory.getLogger(JdbcExecuteMethodsService.class);
	// @Autowired automatically injects the DataSource bean into this class.
	// The DataSource object provides the necessary database connection management.
	// Spring will resolve this dependency and inject the DataSource configured in
	// the application context.
	@Autowired
	private DataSource dataSource;
	


	public Integer insertMovies() {
		String query = "INSERT INTO movie (title, genre, language, duration, description) "
				+ "VALUES ('Stranger Things', 'Sci-Fi', 'English', 50, 'A group of kids uncover supernatural forces and government secrets in a small town.')";

		log.info("Starting insertMovies() - preparing to execute insert query");

		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

			log.debug("Database connection established successfully");

			int updateCount = statement.executeUpdate(query);
			log.info("Insert query executed successfully. Number of rows inserted: {}", updateCount);

			return updateCount;

		} catch (Exception e) {
			log.error("Exception occurred while inserting movie: {}", e.getMessage(), e);
		}

		log.warn("Insert operation failed - returning 0 rows inserted");
		return 0;
	}

	public List<MovieDTO> fetchMovies() {
		String query = "SELECT * FROM public.movie";

		List<MovieDTO> movieDTOList = new ArrayList<>();
		MovieMapper movieMapper = new MovieMapper(); // Create an instance of MovieMapper

		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

			log.debug("Database connection established successfully");

			ResultSet resultSet = statement.executeQuery(query);

			// Iterate over the ResultSet
			while (resultSet.next()) {
				// Map each record in the result set to a MovieDTO
				Movie movieEntity = new Movie();

				movieEntity.setTitle(resultSet.getString("title"));
				movieEntity.setGenre(resultSet.getString("genre"));
				movieEntity.setLanguage(resultSet.getString("language"));
				movieEntity.setDuration(resultSet.getInt("duration"));
				movieEntity.setDescription(resultSet.getString("description"));

				// Convert Movie entity to MovieDTO using MovieMapper
				MovieDTO movieDTO = movieMapper.convertToDTO(movieEntity);

				// Add the MovieDTO to the list
				movieDTOList.add(movieDTO);
			}

			log.debug("Successfully mapped and retrieved {} movie records", movieDTOList.size());

		} catch (Exception e) {
			log.error("Error while fetching movies: {}", e.getMessage(), e);
		}
		return movieDTOList;
	}

}
