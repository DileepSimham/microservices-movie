package com.movie.config;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@ConfigurationProperties(prefix = "movie.service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieServiceConfig {
    private String name;
    private String description;
    private String version;
    private List<String> supportedGenres;
    private String defaultLanguage;
    private int maxMovies;
    private Map<String, String> apiKeys;
    private Map<String, Boolean> featureFlags;

    // Getters and Setters for all fields
}
