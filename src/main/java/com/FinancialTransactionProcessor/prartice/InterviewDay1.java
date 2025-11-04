package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class InterviewDay1 {

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

    //🧠 Find the highest-paid employee in each department.
        Map<String, Employee> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(
                                Employee::getSalary
                        )),
                        Optional::get
                )));
        collect.forEach((dept, emp) ->
                System.out.println( "employees with age and dept are here" + " → " + emp.getName() + " (" + emp.getSalary() + ")"));

//Find the most experienced (oldest) employee in that department.
        Optional<Employee> engineering = employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .max(Comparator.comparingInt(Employee::getAge));
        System.out.println("here is the list" + engineering);

        OptionalDouble average = employees.stream()
                .filter(employee -> employee.getAge() < 30)
                .mapToDouble(Employee::getSalary)
                .average();

        System.out.println("here is the list of the avg salary " + average);



    }
}
