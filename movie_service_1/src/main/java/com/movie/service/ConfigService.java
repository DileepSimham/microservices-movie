package com.movie.service;

import com.movie.config.MovieServiceConfig;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final MovieServiceConfig config;

    public ConfigService(MovieServiceConfig config) {
        this.config = config;
    }

    public MovieServiceConfig getConfig() {
        return config;
    }
}
