package com.FinancialTransactionProcessor.prartice;

import com.FinancialTransactionProcessor.prartice.StreamPractice3rdSet.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class StreamPractice3rdSet {

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

        // ✅ Example 21: Find 2nd highest salary employee
        Employee secondHighest = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .skip(1)
                .findFirst()
                .orElse(null);
        System.out.println("\n21️⃣ 2nd highest salary: " +
                (secondHighest != null ? secondHighest.getName() : "N/A"));

        // ✅ Example 22: Find duplicate department names
        List<String> stringList = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("\n22️⃣ Duplicate departments: " + stringList);

        List<String> stringList1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println("\n22️⃣ Duplicate departments: " + stringList1);

        List<Integer> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getAge, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // ✅ Example 23: Find employee with longest name

        Employee longestName = employees.stream()
                .max(Comparator.comparing(employee -> employee.getName().length()))
                .orElse(null);
        System.out.println("\n22️⃣ Duplicate departments: " + longestName);

        Employee shortName = employees.stream()
                .min(Comparator.comparing(employee -> employee.getName().length()))
                .orElse(null);
        System.out.println("\n22️⃣ Duplicate departments: " + shortName);

        // ✅ Example 25: Find department with highest total salary
        String higestSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
        System.out.println("\n25️⃣ Department with highest total salary: " + higestSalary);

        List<String> names = Arrays.asList("Shubham", "Amit", "Suresh", "Neha");
        List<String> sNames = names.stream()
                .filter(s -> s.startsWith("S"))
                .collect(Collectors.toList());
        System.out.println(sNames);

        List<String> stringList2 = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
        System.out.println(stringList2);

        List<Integer> nums = Arrays.asList(5, 1, 3, 9);

        nums.stream()
                .sorted()
                .collect(Collectors.toUnmodifiableList());

        nums.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toUnmodifiableList());





    }
}
