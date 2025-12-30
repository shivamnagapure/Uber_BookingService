package com.example.uberbookingservice.services;

import com.example.uberbookingservice.dto.DriverAssignedEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SocketRestClient {

    private final WebClient webClient;

    public SocketRestClient(WebClient.Builder builder){
        this.webClient = builder.baseUrl("http://UberSocketServer").build();
    }

    public void notifyPassenger(DriverAssignedEvent event) {
        webClient.post()
                .uri("/api/socket/notify-driver-assigned")
                .bodyValue(event)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
