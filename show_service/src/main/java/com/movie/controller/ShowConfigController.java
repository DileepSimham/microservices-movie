package com.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.config.ShowServiceConfig;
import com.movie.responseDTO.ShowServiceConfigResponse;

@RestController
@RequestMapping("/show-service")
public class ShowConfigController {

    @Autowired
    private ShowServiceConfig config;

    @GetMapping("/config")
    public ShowServiceConfigResponse getShowServiceConfig() {
        return new ShowServiceConfigResponse(
                config.getName(),
                config.getDescription(),
                config.getVersion(),
                config.getSupportedLanguages(),
                config.getDefaultLanguage(),
                config.getMaxShows(),
                config.getFeatureFlags()
        );
    }
}
