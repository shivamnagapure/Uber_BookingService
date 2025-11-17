package com.example.uberbookingservice.configurations;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    // It returns Bean of WebClient

    @Bean
    @LoadBalanced  // This enables service discovery for WebClient
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
