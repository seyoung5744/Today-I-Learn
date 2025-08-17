package com.example.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration
public class FilterConfig {

    Environment env;

    public FilterConfig(Environment env) {
        this.env = env;
    }

//    @Bean
    public RouteLocator getRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/first-service/**")
                        .filters(f -> f
                                .addRequestHeader("first-request", "1st-request-header-by-java")
                                .addResponseHeader("first-response", "1st-response-header-from-java"))
                        .uri("http://localhost:8081")
                )
                .route(r -> r
                        .path("/second-service/**")
                        .filters(f -> f
                                .addRequestHeader("second-request", "2st-request-header-by-java")
                                .addResponseHeader("second-response", "2st-response-header-from-java"))
                        .uri("http://localhost:8082"))
                .build();
    }


}
