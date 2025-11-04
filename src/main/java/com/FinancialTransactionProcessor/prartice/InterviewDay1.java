package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
                System.out.println("employees with age and dept are here" + " → " + emp.getName() + " (" + emp.getSalary() + ")"));

//Find average salary per department.
        Map<String, Double> stringDoubleMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(
                                Employee::getSalary
                        ))
                );
        System.out.println("Avg highest salary: " + stringDoubleMap);

        //Count employees in each department.
        Map<String, Long> collect1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        System.out.println("Dept count: " + collect1);

        //Get a list of unique departments.
        List<String> stringList1 = employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toList());
        System.out.println("uNIQUE DEPTS " + stringList1);

        //Sort employees by salary descending, then by name ascending.
        List<Employee> collect2 = employees.stream()
                .sorted(Comparator.comparing(Employee::getName).thenComparing(Comparator.comparingDouble(Employee::getSalary).reversed()))
                .collect(Collectors.toList());
        System.out.println("sorted names and slaary " + collect2);


//Find second-highest salary in the list of employees.
        Optional<String> first = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .map(Employee::getName)
                .findFirst();
        System.out.println("Employee with 2nd highest salary: " + first);

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

        List<String> stringList = employees.stream()
                .filter(employee -> employee.getName().startsWith("A"))
                .map(Employee::getName)
                .collect(Collectors.toList());
        System.out.println("Starts with " + stringList);
        
        //Partition employees into salary > 50K and salary ≤ 50K using partitioningBy().

        Map<Boolean, List<Employee>> collect3 = employees.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getSalary() > 50000 ));
        System.out.println("Starts with " + collect3);

        //Find the youngest employee in each department.
        Map<String, Employee> collect4 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.collectingAndThen(
                        Collectors.minBy(
                                Comparator.comparingInt(Employee::getAge)
                        ), Optional::get
                )));
        System.out.println("Starts with " + collect4);

        //Find the Oldest employee in each department.
        Map<String, Employee> collect5 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Employee::getAge)
                                ), Optional::get

                        )
                ));
        System.out.println("Starts with " + collect5);

        //Get the employee with the longest name.
        Employee employee1 = employees.stream()
                .max(Comparator.comparing(employee -> employee.getName().length()))
                .orElse(null);

        //get the employee with higest age
        Employee employee = employees.stream()
                .max(Comparator.comparingInt(value -> value.getAge()))
                .orElse(null);


    }
}
