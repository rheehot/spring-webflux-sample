package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@Slf4j
public class DemoApplication {

    /**
     * 스프링이 onSubscribe 를 한다.
     * publisher는 COLD, HOT Type이 존재한다.
     * 하나의 퍼블리셔는 여러개의 subscriber를 가질수 있다. 같은 결과가 나오면 -> COLD 타입
     * block() 메서드는 내부적으로 subscribe()메서드가 동작하고, 결과값을 꺼내올 수 있다.
     */
    @GetMapping("/")
    public Mono<String> hello() {
        log.info("pos1");
        Mono<String> m = Mono.fromSupplier(() -> generateHello()).doOnNext(c -> log.info(c)).log();
        String res = m.block();// subscribe 1번 호출
        log.info("pos2: + " + res);
        return m; // subscribe 1번더 호출 by 스프링
    }

    @GetMapping("/event/{id}")
    public Mono<Event> eventMono(@PathVariable long id) {
        return Mono.just(new Event(id, "event " + id));

    }

    public String generateHello() {
        log.info("generate hello method");
        return "Hello";
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Data
    @AllArgsConstructor
    public static class Event {
        long id;
        String value;
    }
}
