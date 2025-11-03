package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPractice {

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

        // ✅ Example 11: Get names of top 3 highest-paid employees
        List<String> top3 = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + top3);

        // ✅ Example 11: Get names of top 3 highest-paid employees
        List<String> roleBased = employees.stream()
                .filter(employee -> employee.getDepartment().equals("Engineering"))
                .sorted(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getAge))
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + top3);


        double sales = employees.stream()
                .filter(employee -> employee.getDepartment().equals("Sales"))
                .sorted(Comparator.comparing(Employee::getAge).reversed())
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0);
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + sales);


        Map<String, Map<String, List<Employee>>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.groupingBy(Employee::getName)));
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + collect);

        Map<String, Long> collect1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + collect1);

        Map<String, Double> stringDoubleMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + stringDoubleMap);


        Map<String, Double> stringDoubleMap1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName, Collectors.averagingDouble(Employee::getAge)));
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + stringDoubleMap1);

        Employee maxAge = employees.stream()
                .max(Comparator.comparing(Employee::getAge))
                .orElse(null);
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + maxAge);

        Employee minSlaray = employees.stream()
                .min(Comparator.comparing(Employee::getSalary))
                .orElse(null);
        System.out.println("\n11️⃣ Top 3 Highest Paid: " + minSlaray);

        // ✅ Example 16: Find employee names who earn between 50k–80k
        List<String> midRange = employees.stream()
                .filter(e -> e.getSalary() >= 50000 && e.getSalary() <= 80000)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n16️⃣ Salary between 50k–80k: " + midRange);

        // ✅ Example 17: Find first employee in Engineering
        Employee engineering = employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .findFirst()
                        .orElse(null);

        // ✅ Example 18: Check if any employee earns > 1L
        List<String> stringList = employees.stream()
                .filter(employee -> employee.getSalary() > 100000)
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n18️⃣ Any employee earning >1L? " + stringList);

        boolean allAbove20 = employees.stream()
                .anyMatch(employee -> employee.getSalary() > 10000);
        System.out.println("\n19️⃣ All above age 20? " + allAbove20);

        boolean match = employees.stream()
                .anyMatch(employee -> employee.getAge() > 21);
        System.out.println("\n19️⃣ All above age 20? " + match);

        // ✅ Example 19: Check if all employees are above age 20

        boolean match1 = employees.stream()
                .allMatch(employee -> employee.getAge() > 21);
        System.out.println("\n19️⃣ All above age 20? " + match1);

        // ✅ Example 20: Get comma-separated employee names sorted alphabetically
        String joinedNames = employees.stream()
                .map(Employee::getName)
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println("\n20️⃣ Sorted comma-separated names: " + joinedNames);




    }
}
