package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class InterviewDay2 {

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
                new Employee(8, "Manish", 47000, "Support", 24),
                new Employee(9, "Aisha", 90000, "Engineering", 32)
        );

        // 1️⃣ Get distinct departments
        employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toList());

        // 2️⃣ Find employee with maximum salary
        employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));

        // 3️⃣ Find minimum salary employee in Engineering department
        employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase("Engineering"))
                .min(Comparator.comparingDouble(Employee::getSalary));

        // 4️⃣ Count employees in each department
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        // 5️⃣ Sort employees by salary in descending order
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed());

        // 6️⃣ Get sorted list of employee names alphabetically
        employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 7️⃣ Get names of employees starting with 'A'
        employees.stream()
                .filter(e -> e.getName().startsWith("A"))
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 8️⃣ Find department with minimum average salary
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);


        employees.stream()
                        .collect(Collectors.groupingBy(Employee::getSalary,Collectors.counting()));

        // 9️⃣ Partition employees by age > 30
        employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getAge() > 30));

        // 🔟 Find department with maximum average salary
        String s = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        // 11️⃣ Salary statistics (sum, avg, min, max, count)
        DoubleSummaryStatistics salaryStats = employees.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        salaryStats.getSum();
        salaryStats.getMin();
        salaryStats.getAverage();
        salaryStats.getCount();

        // 12️⃣ Departments having more than 1 employee
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey);

        // 13️⃣ Find 2nd highest salary employee
        Employee e = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst()
                .orElse(null);
        System.out.println("2nd Highest Salary Employee: " + e.getName() + " - " + e.getSalary());

        // 14️⃣ Get employees grouped by department (with employee names)
        Map<String, List<String>> namesByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getName, Collectors.toList())));

        // 15️⃣ Total salary per department
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(Employee::getSalary)));

        // 16️⃣ Find duplicate employee names
        List<String> duplicateNames = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 17️⃣ Filter employees with salary < 80,000
        employees.stream()
                .filter(e1 -> e1.getSalary() < 80000)
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 18️⃣ Get highest paid employee per department
        Map<String, Optional<Employee>> topPerDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))));

        System.out.println("\nTop Salary Employee per Department:");
        topPerDept.forEach((dept, emp) ->
                emp.ifPresent(employee -> System.out.println(dept + " → " + employee.getName() + " (" + employee.getSalary() + ")"))
        );

        // 19️⃣ Filter employees with salary > 50k, sort by name ascending, collect only names
        employees.stream()
                .filter(e2 -> e2.getSalary() > 50000)
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .collect(Collectors.toList());

        // 20️⃣ Get departments having more than 2 employees
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 21️⃣ Get departments with average salary > 70,000
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 70000)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // 22️⃣ Get top 5 highest-paid employees
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(5)
                .collect(Collectors.toList());

        // 23️⃣ Get list of Engineering employees
        employees.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase("Engineering"))
                .map(Employee::getName)
                .collect(Collectors.toUnmodifiableList());

        // 24️⃣ Get 3 youngest employees
        employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .limit(3)
                .collect(Collectors.toList());

        // 25️⃣ Average salary of all employees
        employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);

        // 26️⃣ Distinct departments
        employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toSet());

        // 27️⃣ Departments with more than one employee
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        // 28️⃣ Partition employees by age < 30
        employees.stream()
                .collect(Collectors.partitioningBy(e4 -> e4.getAge() < 30));

        // 29️⃣ Example Predicate and Function usage
        Predicate<Employee> highSalary = emp -> emp.getSalary() > 50000;
        Function<Employee, String> getName = Employee::getName;
    }

    // 🔠 Check if two strings are anagrams
    public static boolean areAnagramsTraditional(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        String upperCase1 = s1.toUpperCase();
        String upperCase2 = s2.toUpperCase();
        char[] arr1 = upperCase1.toCharArray();
        char[] arr2 = upperCase2.toCharArray();
        Arrays.sort(arr1);
        Arrays.sort(arr2);
        return Arrays.equals(arr1, arr2);
    }

}
