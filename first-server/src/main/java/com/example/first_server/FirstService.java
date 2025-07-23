package com.example.first_server;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FirstService {

    private final WebClient webClient;

    public FirstService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String getFromSecondServer() {
        Mono<String> data = webClient
                .get()
                .uri("http://localhost:8444/hi")
                .retrieve()
                .bodyToMono(String.class);

        return data.block();
    }
}
