package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

/**
 * @author Geonguk Han
 * @since 2020-10-13
 */
@RestController
@Slf4j
public class APIController {

    static final String URL1 = "http://localhost:8081/service?req={req}";

    @Autowired
    MyService myService;

    WebClient client = WebClient.create();

    @GetMapping("/rest")
    public Mono<String> rest(int idx) {

        return client.get().uri(URL1, idx).exchange()       // Mono<ClientResponse>
                .flatMap(c -> c.bodyToMono(String.class))   // Mono<String>
                .flatMap(str -> client.get().uri(URL1, str).exchange())     // Mono<ClientResponse>
                .flatMap(c -> c.bodyToMono(String.class))      // Mono<String>
                .flatMap(res -> Mono.fromCompletionStage(myService.work(res)));
    }

    @Service
    public static class MyService {

        @Async
        public CompletableFuture<String> work(String req) {
            return CompletableFuture.completedFuture(req + "/async");
        }
    }
}
