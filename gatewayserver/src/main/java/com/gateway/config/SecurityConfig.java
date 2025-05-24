package com.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration // Marks this as a configuration class
@EnableWebFluxSecurity // Enables Spring Security for WebFlux (reactive web apps)
public class SecurityConfig {

    // Define the security filter chain — this is where security rules are configured
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {

        // Set up authorization rules for routes
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges

                        // Allow access to this path only if the JWT token has ROLE_MOVIES
                        .pathMatchers("/movieMicroservice/movieservice/**").hasRole("MOVIES")

                        // Similarly, only users with ROLE_SHOWS can access showservice
                        .pathMatchers("/movieMicroservice/showservice/**").hasRole("SHOWS"))

                // Configure the resource server to validate JWT tokens
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec

                        // Attach a custom converter to extract roles from the token
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));

        // Disable CSRF since it's not needed for stateless APIs using JWT
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());

        // Build and return the security web filter chain
        return serverHttpSecurity.build();
    }

    // Custom converter to extract roles from the JWT token
    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        // This is the default converter used by Spring Security to extract authorities
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        // Here you plug in your custom KeycloakRoleConverter to extract roles properly from token
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // Since we're in a reactive app, we need to wrap the converter with an adapter
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }
}
