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
    public static class Employee {
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


        // 🧠 Q1: Find the highest-paid employee in each department.
        Map<String, Employee> highestPaidByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)),
                                Optional::get
                        )
                ));

        highestPaidByDept.forEach((dept, emp) ->
                System.out.println("Highest-paid in " + dept + " → " + emp.getName() + " (" + emp.getSalary() + ")")
        );


        // 🧠 Q2: Find the average salary per department.
        Map<String, Double> avgSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        System.out.println("Average salary per department: " + avgSalaryByDept);


        // 🧠 Q3: Find the highest salary per department.
        Map<String, Optional<Employee>> highestSalaryPerDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary))
                ));
        System.out.println("Highest salary per department: " + highestSalaryPerDept);


        // 🧠 Q4: Find departments whose total salary exceeds 150,000.
        List<String> departmentsWithHighTotal = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingDouble(Employee::getSalary)))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 150000)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        System.out.println("Departments with total salary > 150000: " + departmentsWithHighTotal);


        // 🧠 Q5: Find average age per department.
        Map<String, Double> avgAgeByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getAge)));

        System.out.println("Average age per department: " + avgAgeByDept);


        // 🧠 Q6: Group Engineering employees with salary > 80K by age > 30.
        Map<Boolean, List<Employee>> engineeringGroupedByAge = employees.stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase("Engineering") && emp.getSalary() > 80000)
                .collect(Collectors.groupingBy(emp -> emp.getAge() > 30));

        System.out.println("Engineering employees grouped by age > 30: " + engineeringGroupedByAge);


        // 🧠 Q7: Count employees in each department.
        Map<String, Long> empCountByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        System.out.println("Employee count per department: " + empCountByDept);


        // 🧠 Q8: Get a list of unique departments.
        List<String> uniqueDepartments = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toList());

        System.out.println("Unique Departments: " + uniqueDepartments);


        // 🧠 Q9: Sort employees by name (ascending) and salary (descending).
        List<Employee> sortedEmployees = employees.stream()
                .sorted(
                        Comparator.comparing(Employee::getName)
                                .thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed())
                )
                .collect(Collectors.toList());

        System.out.println("Sorted employees (name asc, salary desc): " + sortedEmployees);


        // 🧠 Q10: Find the second-highest salary in the list.
        Optional<String> secondHighestSalaryEmployee = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .map(Employee::getName)
                .findFirst();

        System.out.println("Employee with 2nd highest salary: " + secondHighestSalaryEmployee.orElse("N/A"));


        // 🧠 Q11: Find the most experienced (oldest) employee in the Engineering department.
        Optional<Employee> oldestInEngineering = employees.stream()
                .filter(e -> e.getDepartment().equalsIgnoreCase("Engineering"))
                .max(Comparator.comparingInt(Employee::getAge));

        System.out.println("Oldest in Engineering: " + oldestInEngineering.orElse(null));


        // 🧠 Q12: Find the average salary of employees younger than 30.
        OptionalDouble avgSalaryUnder30 = employees.stream()
                .filter(e -> e.getAge() < 30)
                .mapToDouble(Employee::getSalary)
                .average();

        System.out.println("Average salary (age < 30): " + avgSalaryUnder30.orElse(0));


        // 🧠 Q13: Get list of employees whose names start with 'A'.
        List<String> namesStartingWithA = employees.stream()
                .filter(e -> e.getName().startsWith("A"))
                .map(Employee::getName)
                .collect(Collectors.toList());

        System.out.println("Employees with names starting with 'A': " + namesStartingWithA);


        // 🧠 Q14: Partition employees into salary > 50K and salary ≤ 50K.
        Map<Boolean, List<Employee>> partitionedBySalary = employees.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 50000));

        System.out.println("Employees partitioned by salary (>50K): " + partitionedBySalary);


        // 🧠 Q15: Find the youngest employee in each department.
        Map<String, Employee> youngestByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.minBy(Comparator.comparingInt(Employee::getAge)),
                                Optional::get
                        )
                ));

        System.out.println("Youngest employee in each department: " + youngestByDept);


        // 🧠 Q16: Find the oldest employee in each department.
        Map<String, Employee> oldestByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getAge)),
                                Optional::get
                        )
                ));

        System.out.println("Oldest employee in each department: " + oldestByDept);


        // 🧠 Q17: Get the employee with the longest name.
        Employee longestNameEmployee = employees.stream()
                .max(Comparator.comparing(e -> e.getName().length()))
                .orElse(null);

        System.out.println("Employee with the longest name: " + longestNameEmployee);


        // 🧠 Q18: Get the employee with the highest age.
        Employee oldestEmployee = employees.stream()
                .max(Comparator.comparingInt(Employee::getAge))
                .orElse(null);

        System.out.println("Employee with the highest age: " + oldestEmployee);
    }
}
