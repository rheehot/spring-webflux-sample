package com.example.demo.employee;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Andrew
 * @since 2020-10-11
 */
@Repository
public class EmployeeRepository {
    private static Map<String, Employee> map = new ConcurrentHashMap<>();


    static {
        map.put("1", new Employee("1", "andrew"));
    }

    public Mono<Employee> findByEmployeeById(String id) {
        return Mono.just(map.get(id));
    }

    public Flux<Employee> findAllEmployees() {
        return null;
    }
}
