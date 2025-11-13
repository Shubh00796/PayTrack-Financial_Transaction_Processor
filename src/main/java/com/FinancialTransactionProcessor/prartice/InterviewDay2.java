package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
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

        employees.stream()
                .filter(employee -> employee.getSalary() > 850000)
                .map(Employee::getSalary)
                .collect(Collectors.toList());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .max(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);

        employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .min(Comparator.comparingDouble(Employee::getSalary))
                .orElse(null);

        employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .collect(Collectors.averagingDouble(Employee::getAge));

        Map<String, DoubleSummaryStatistics> stats = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summarizingDouble(Employee::getSalary)));
        
stats.forEach((s, doubleSummaryStatistics) -> System.out.println("dept" + doubleSummaryStatistics.getMin()));


        Map<String, DoubleSummaryStatistics> statisticsMap = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summarizingDouble(Employee::getAge)));






        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summarizingDouble(
                        Employee::getSalary
                )));

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .collect(Collectors.toList());

        List<String> stringList = employees.stream()
                .collect(Collectors.groupingBy(Employee::getName))
                .entrySet().stream()
                .filter(stringListEntry -> stringListEntry.getValue().size() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        employees.stream()
                .filter(employee -> employee.getAge() < 30 && employee.getAge() < 35 && employee.getAge() > 35)
                .collect(Collectors.summarizingDouble(Employee::getAge));


        Employee e = employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst()
                .orElse(null);
        System.out.println("2nd Highest Salary Employee: " + e.getName() + " - " + e.getSalary());

        Map<String, List<String>> collect = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(
                        Employee::getName, Collectors.toList()
                )));

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(
                        Employee::getSalary
                )));

        DoubleSummaryStatistics doubleSummaryStatistics = employees.stream()
                .mapToDouble(Employee::getSalary)
                .summaryStatistics();

        doubleSummaryStatistics.getAverage();
        doubleSummaryStatistics.getMax();
        doubleSummaryStatistics.getMin();


        employees.stream()
                .collect(Collectors.toMap(Employee::getId, Employee::getName));

        employees.stream()
                .filter(employee -> employee.getName().startsWith("A"))
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .collect(Collectors.toList());


        employees.stream()
                .map(employee -> employee.getSalary() * employee.getSalary())
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        Map<String, Optional<Employee>> topPerDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.maxBy(
                        Comparator.comparingDouble(Employee::getSalary)
                )));

        System.out.println("\nTop Salary Employee per Department:");
        topPerDept.forEach((dept, emp) ->
                emp.ifPresent(employee -> System.out.println(dept + " → " + e.getName() + " (" + e.getSalary() + ")"))
        );

        //ilter employees with salary > 50k, sort by name ascending, and collect only names in a list.
        employees.stream()
                .filter(employee -> employee.getSalary() > 50000)
                .sorted(Comparator.comparing(Employee::getName))
                .map(Employee::getName)
                .collect(Collectors.toList());

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .skip(1)
                .findFirst()
                .map(Employee::getName)
                .orElse(null);

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(3)
                .map(Employee::getName)
                .collect(Collectors.toUnmodifiableList());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingInt(
                        Employee::getAge
                )));

        //Get list of departments having more than 2 employees
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //Get list of departments with average salary greater than 70,000
        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(
                        Employee::getSalary)))
                .entrySet().stream()
                .filter(stringDoubleEntry -> stringDoubleEntry.getValue() > 70000)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        //Sort the list of employees by salary descending and collect the top 5 highest-paid employees using Streams.

        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .limit(5)
                .collect(Collectors.toList());

        employees.stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase("Engineering"))
                .map(Employee::getName)
                .collect(Collectors.toUnmodifiableList());

        employees.stream()
                .sorted(Comparator.comparingInt(Employee::getAge))
                .limit(3)
                .collect(Collectors.toList());

        employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.summingDouble(
                        Employee::getSalary
                )));

        employees.stream()
                .map(Employee::getDepartment)
                .distinct()
                .collect(Collectors.toSet());

        employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        employees.stream()
                .collect(Collectors.partitioningBy(employee -> employee.getAge() < 30));


        Predicate<Employee> highSalary = employee -> employee.getSalary() > 50000;

        Function<Employee, String> getName = Employee::getName;


    }


    //CHECK IF STRING IS ANAGRAM SILENT = LISTEN => ANAGRAM
    public static boolean areAnagramsTraditional(String s1, String s2) {
        if (s1.length() != s2.length()) return false;
        String upperCase1 = s1.toUpperCase();
        String upperCase = s2.toUpperCase();
        char[] charArray = upperCase1.toCharArray();
        char[] charArray1 = upperCase.toCharArray();
        Arrays.sort(charArray1);
        Arrays.sort(charArray);
        return Arrays.equals(charArray1, charArray);
    }
//    public static boolean arePalindromeTraditional(String s1) {
//        if( s1.length() != s2.length()) return  false;


}
