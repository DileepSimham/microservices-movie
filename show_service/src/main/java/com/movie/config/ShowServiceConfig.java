package com.movie.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "show.service")
public class ShowServiceConfig {

    private String name;
    private String description;
    private String version;
    private List<String> supportedLanguages;
    private String defaultLanguage;
    private int maxShows;
    private Map<String, Boolean> featureFlags;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(List<String> supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public int getMaxShows() {
        return maxShows;
    }

    public void setMaxShows(int maxShows) {
        this.maxShows = maxShows;
    }

    public Map<String, Boolean> getFeatureFlags() {
        return featureFlags;
    }

    public void setFeatureFlags(Map<String, Boolean> featureFlags) {
        this.featureFlags = featureFlags;
    }
}
