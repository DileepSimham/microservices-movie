package com.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDTO {

	private String title;
	private String genre;
	private String language;
	private int duration; // in minutes
	private String description;
}
