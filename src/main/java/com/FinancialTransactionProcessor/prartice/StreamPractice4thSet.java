package com.FinancialTransactionProcessor.prartice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamPractice4thSet {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Task {  // 👈 made static so it can be used inside main
        private int id;
        private String name;
        private int priority;       // Higher = more important
        private LocalDate createdAt;
    }

    public static void main(String[] args) {
        List<Task> tasks = Arrays.asList(
                new Task(1, "Database Setup", 2, LocalDate.of(2024, 12, 10)),
                new Task(2, "API Development", 5, LocalDate.of(2025, 1, 5)),
                new Task(3, "Frontend Fixes", 3, LocalDate.of(2025, 2, 15))
        );

        tasks.stream()
                .max(Comparator.comparing(Task::getCreatedAt))
                .orElse(null);

        tasks.stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));

        tasks.stream()
                .map(Task::getId)
                .collect(Collectors.toList());

        Map<Integer, String> collect = tasks.stream()
                .collect(Collectors.toMap(
                        task -> task.getId(),
                        Task::getName
                ));

        Map<Integer, List<Task>> collect1 = tasks.stream()
                .collect(Collectors.groupingBy(Task::getPriority));

        List<String> stringList = tasks.stream()
                .collect(Collectors.groupingBy(Task::getName, Collectors.counting()))
                .entrySet().stream()
                .filter(stringLongEntry -> stringLongEntry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());


        tasks.stream()
                .collect(Collectors.partitioningBy(task -> task.getPriority() > 3));



    }
}
