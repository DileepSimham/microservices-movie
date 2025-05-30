package com.movie.responseDTO;

import com.movie.entity.Show;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowResponseDTO {
    private Show show;
    private MovieDTO movie;
}
