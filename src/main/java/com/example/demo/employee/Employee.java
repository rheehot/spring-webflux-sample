package com.example.demo.employee;

import lombok.Data;

/**
 * @author Andrew
 * @since 2020-10-11
 */
@Data
public class Employee {

    private String id;
    private String name;

    public Employee() {
    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
