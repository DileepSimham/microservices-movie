package com.movie.mapper;

import com.movie.dto.MovieDTO;
import com.movie.entity.Movie;  // Ensure you import the correct Movie entity class

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MovieMapper {

    // Manually converting MovieEntity (Movie) to MovieDTO
    public MovieDTO convertToDTO(Movie movieEntity) {
        if (movieEntity == null) {
            return null; // Return null if the entity is null
        }
        
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setGenre(movieEntity.getGenre());
        movieDTO.setLanguage(movieEntity.getLanguage());
        movieDTO.setDuration(movieEntity.getDuration());
        movieDTO.setDescription(movieEntity.getDescription());
        
        return movieDTO;
    }

    // Manually converting MovieDTO to MovieEntity (if needed)
    public Movie convertToEntity(MovieDTO movieDTO) {
        if (movieDTO == null) {
            return null; // Return null if the DTO is null
        }
        
        Movie movieEntity = new Movie();
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setGenre(movieDTO.getGenre());
        movieEntity.setLanguage(movieDTO.getLanguage());
        movieEntity.setDuration(movieDTO.getDuration());
        movieEntity.setDescription(movieDTO.getDescription());
        
        return movieEntity;
    }
}
