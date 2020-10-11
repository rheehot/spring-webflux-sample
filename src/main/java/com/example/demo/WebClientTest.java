package com.example.demo;

import com.example.demo.employee.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Andrew
 * @since 2020-10-10
 */
@Component
@Slf4j
public class WebClientTest {
    private static WebClient webClient;

    static {
        webClient = WebClient.create("http://localhost:8080");
    }

    public static void main(String[] args) {
        Mono<Employee> employeeMono = webClient.get()
                .uri("/employees/{id}", "1")
                .retrieve()
                .bodyToMono(Employee.class);
        
        System.out.println(employeeMono.block());
        employeeMono.subscribe(System.out::println);
    }

}
