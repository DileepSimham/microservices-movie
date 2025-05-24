package com.gateway;

import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // Marks this as a Spring Boot application
public class GatewayserverApplication {

	public static void main(String[] args) {
		// Bootstraps the Spring Boot application
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	// This Bean method defines custom routing logic for Spring Cloud Gateway
	@Bean
	public RouteLocator movieMicroserviceRouteConfig(RouteLocatorBuilder routeLocatorBuilder) {
		return routeLocatorBuilder.routes()

				// ---------- 1st Route: For MOVIESERVICE ----------
				.route(p -> p
						// Match any incoming HTTP requests that start with this path
						.path("/movieMicroservice/movieservice/**")

						// Filters let you manipulate the request/response.
						// rewritePath() strips the /movieMicroservice/movieservice/ part and passes on the rest
						// For example: /movieMicroservice/movieservice/movies/getMovies => /movies/getMovies
						.filters(f -> f.rewritePath("/movieMicroservice/movieservice/(?<segment>.*)", "/${segment}")

								// Adds a custom header to the response with the time the response was generated
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))

						// Route the request to the MOVIESERVICE (registered in Eureka or service discovery)
						.uri("lb://MOVIESERVICE"))

				// ---------- 2nd Route: For SHOWSERVICE ----------
				.route(p -> p
						// Match paths for the show service
						.path("/movieMicroservice/showservice/**")

						// Again, rewrite the path to remove the /movieMicroservice/showservice prefix
						// For example: /movieMicroservice/showservice/shows/all => /shows/all
						.filters(f -> f.rewritePath("/movieMicroservice/showservice/(?<segment>.*)", "/${segment}")

								// Add same response header with current time
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))

						// Route to the SHOWSERVICE (another microservice registered in Eureka)
						.uri("lb://SHOWSERVICE"))

				// Finish building the route definitions
				.build();
	}
}
