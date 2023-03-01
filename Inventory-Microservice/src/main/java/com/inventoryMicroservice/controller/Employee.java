package com.inventoryMicroservice.controller;

import java.util.*;
import java.util.stream.Collectors;

public class Employee {
    int id;
    String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void main(String[] args) {
        List<Employee> list = Arrays.asList(new
                Employee(1, "raja"), new Employee(2, "santosh"), new Employee(3, "srikant"));

        Map<Integer, Map<Integer, String>> map = list.stream()
                .collect(Collectors.groupingBy(Employee::getId,
                        Collectors.toMap(Employee::getId, Employee::getName)));
        System.out.println(map);

        String s1 = new String("raja");
        String s2 = "raja";

        System.out.println(s1 == s2); //
        System.out.println(s1.equals(s2)); //
    }
}
