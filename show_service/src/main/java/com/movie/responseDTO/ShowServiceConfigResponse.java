package com.movie.responseDTO;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShowServiceConfigResponse {

    private String name;
    private String description;
    private String version;
    private List<String> supportedLanguages;
    private String defaultLanguage;
    private int maxShows;
    private Map<String, Boolean> featureFlags;

    // Constructors, Getters, Setters

    public ShowServiceConfigResponse(String name, String description, String version,
                                     List<String> supportedLanguages, String defaultLanguage,
                                     int maxShows, Map<String, Boolean> featureFlags) {
        this.name = name;
        this.description = description;
        this.version = version;
        this.supportedLanguages = supportedLanguages;
        this.defaultLanguage = defaultLanguage;
        this.maxShows = maxShows;
        this.featureFlags = featureFlags;
    }

    // Getters and setters here...
}
