package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class DemoApplication {

    @GetMapping("/")
    public Mono<String> hello() {
        log.info("pos1");
//        Mono<String> m = Mono.just(generateHello()).doOnNext(c -> log.info(c)).log();
        Mono<String> m = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();
        log.info("pos2");

        return m;
    }

    public String generateHello() {
        log.info("generate hello method");
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
