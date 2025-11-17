package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class InterviewDay3 {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Employee {
        private int id;
        private String name;
        private double salary;
        private String department;
        private int age;
    }

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee(1, "Rohit", 80000, "Engineering", 28),
                new Employee(2, "Aisha", 90000, "Engineering", 32),
                new Employee(3, "Vikram", 45000, "Support", 26),
                new Employee(4, "Sneha", 75000, "Product", 29),
                new Employee(5, "Kunal", 99000, "Engineering", 35),
                new Employee(6, "Priya", 55000, "Sales", 25),
                new Employee(7, "Anita", 88000, "Product", 31),
                new Employee(8, "Manish", 47000, "Support", 24)
        );

        // Filter employees by department

        employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Product"))
                .map(Employee::getName)
                .collect(Collectors.toList());

        employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .map(Employee::getName)
                .collect(Collectors.toList());

        employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));

        employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .collect(Collectors.toList());

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst();

        employees.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getAge() > 30));


        employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getName,Collectors.counting()))
                .entrySet().stream()
                .filter(stringListEntry -> stringListEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(
                        Comparator.comparingDouble(Employee::getSalary))
                ));


        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,Collectors.maxBy(
                        Comparator.comparingInt(Employee::getAge)
                )));

        employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(", "));

        employees.stream()
                .map(Employee::getName)
                .flatMap(s -> Arrays.stream(s.split("")))
                .collect(Collectors.toList());

        employees.stream()
                .map(Employee::getDepartment)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .collect(Collectors.toList());

        employees.stream()
                .mapToDouble(Employee::getSalary)
                .reduce(0.0, Double::sum);

        List<Employee> sorted = employees.stream()
                .sorted(Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Employee::getSalary))
                .collect(Collectors.toList());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,Collectors.minBy(
                        Comparator.comparingInt(Employee::getAge)
                )));

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());


    }
}
