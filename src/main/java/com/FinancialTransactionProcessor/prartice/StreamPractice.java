package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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







    }
}
