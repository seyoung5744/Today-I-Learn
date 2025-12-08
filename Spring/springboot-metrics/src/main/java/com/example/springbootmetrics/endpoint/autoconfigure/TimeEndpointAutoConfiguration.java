package com.example.springbootmetrics.endpoint.autoconfigure;

import com.example.springbootmetrics.endpoint.TimeEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeEndpointAutoConfiguration {

    @Bean
    public TimeEndpoint timeEndpoint() {
        return new TimeEndpoint();
    }
}
