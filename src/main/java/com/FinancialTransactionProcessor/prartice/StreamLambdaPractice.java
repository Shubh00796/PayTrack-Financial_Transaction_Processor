package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

public class StreamLambdaPractice {

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

        // ✅ Example 1: Filter employees
        List<Employee> highEarners = employees.stream()
                .filter(e -> e.getSalary() > 70000)
                .collect(Collectors.toList());
        System.out.println("The collect are"+ highEarners);

        List<Employee> collect = employees.stream()
                .filter(employee -> employee.getSalary() > 55000 && employee.getAge() > 25)
                .collect(Collectors.toList());
        System.out.println("The collect are"+ collect.stream().map(Employee::getSalary));

       List<String> upperNames = employees.stream()
               .map(employee -> employee.getName().toUpperCase())
               .collect(Collectors.toList());
           System.out.println("\n2️⃣ Uppercase names: " + upperNames);


        // ✅ Example 1: SORT employees
        List<Employee> sortedBySalary = employees.stream()
                .sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder()).thenComparing(Employee::getAge))
                .collect(Collectors.toList());
        System.out.println("\n2️⃣ sortedBySalary names: " + sortedBySalary);

        List<Employee> sortedByDept = employees.stream()
                .sorted(Comparator.comparing(Employee::getDepartment,Comparator.reverseOrder()).thenComparing(Employee::getName))
                .collect(Collectors.toList());
        System.out.println("\n4️⃣ First after department+salary sort: " + sortedByDept);

        // ✅ Example 5: Get list of distinct departments
        List<String> distinctDept = employees.stream()
                .map(employee -> employee.getDepartment())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("\n5️⃣ Departments: " + distinctDept);

        // ✅ Example 6: Find the highest-paid employee (reduce)
        Optional<Employee> maxSalaryEmp = employees.stream()
                .reduce((employee, employee2) -> employee.getSalary() > employee2.getSalary() ? employee : employee2);
        System.out.println("\n6️⃣ Highest paid: " + maxSalaryEmp.map(Employee::getName).orElse("N/A"));

        Optional<Employee> higestPaid = employees.stream()
                .reduce((employee, employee2) -> employee.getAge() > employee2.getAge() ? employee : employee2);
        System.out.println("\n6️⃣ Highest aged: " + maxSalaryEmp.map(Employee::getName).orElse("N/A"));

        // ✅ Example 7: Average salary using mapToDouble + average()
        double avgSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
        System.out.println("\n7️⃣ Average salary: " + avgSalary);

        double avgAge = employees.stream()
                .mapToDouble(Employee::getAge)
                .average()
                .orElse(0);
        System.out.println("\n7️⃣ Average avgAge: " + avgAge);



        // ✅ Example 8: Count employees older than 30
        long countOlderThan30 = employees.stream()
                .filter(e -> e.getAge() > 30)
                .count();
        System.out.println("\n8️⃣ Employees older than 30: " + countOlderThan30);

        // ✅ Example 9: Collect names of Engineering employees sorted by salary desc
        List<String> engSorted = employees.stream()
                .filter(e -> e.getDepartment().equals("Engineering"))
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n9️⃣ Engineering by salary desc: " + engSorted);

        // ✅ Example 9: Collect names of Product employees sorted by and Age
        List<String> product = employees.stream()
                .filter(employee -> employee.getDepartment().equals("Product"))
                .sorted(Comparator.comparing(Employee::getSalary).reversed().thenComparing(Employee::getAge).reversed())
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("\n9️⃣ Products by salary desc: " + product);

    }
}
