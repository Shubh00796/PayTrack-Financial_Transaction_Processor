package com.FinancialTransactionProcessor.dday;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * DDay - Java Streams Interview Practice
 *
 * This class demonstrates common Java Stream API interview questions
 * using a sample Employee dataset. The goal is to practice performing
 * different stream operations such as filtering, mapping, sorting,
 * aggregation, and finding min/max values.
 *
 * Covered Stream Problems:
 * 1. Get all employee names
 * 2. Filter employees based on salary
 * 3. Count employees in Engineering department
 * 4. Find employee with highest salary
 * 5. Find employee with lowest salary
 * 6. Get employee names sorted by salary
 * 7. Group employees by department
 * 8. Count employees in each department
 * 9. Find highest paid employee in each department
 * 10. Find average salary in each department
 *
 * This class serves as a practice reference for developers preparing
 * for Java interviews and learning functional-style programming
 * using Java Stream API.
 *
 * More stream-based interview problems can be added here.
 */

public class DDay {

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

        // 1️⃣ Get all employee names
        List<String> employeeNames = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 2️⃣ Get employees whose salary > 80000
        List<Employee> highSalaryEmployees = employees.stream()
                .filter(employee -> employee.getSalary() > 80000)
                .collect(Collectors.toList());

        // 3️⃣ Count employees in Engineering department
        long engineeringEmployeeCount = employees.stream()
                .filter(employee -> "Engineering".equals(employee.getDepartment()))
                .count();

        // 4️⃣ Find the employee with highest salary
        Employee highestSalaryEmployee = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);

        // 5️⃣ Find the employee with lowest salary
        Employee lowestSalaryEmployee = employees.stream()
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);

        // 6️⃣ Get list of employee names sorted by salary
        List<String> employeeNamesSortedBySalary = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 7️⃣ Group employees by department
        Map<String, List<Employee>> employeesByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        // 8️⃣ Count employees in each department
        Map<String, Long> employeeCountByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));

        // 9️⃣ Find highest paid employee in each department
        Map<String, Optional<Employee>> highestPaidEmployeeByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
                ));

        // 🔟 Find average salary in each department
        Map<String, Double> averageSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));



        employees.stream()
                .filter(employee -> employee.getAge() > 30)
                .map(Employee::getName)
                .collect(Collectors.toList());

        employees.stream()
                .filter(employee -> employee.getSalary() > 70000)
                .map(Employee::getName)
                .collect(Collectors.toList());

        employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge));
    }
}