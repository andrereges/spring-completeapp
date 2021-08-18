package br.com.andre.completeapp.utils;

import br.com.andre.completeapp.models.Task;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public class TaskCreator {
    public static Task createOne() {
        return Task.builder()
                .id(1L)
                .name("Task one")
                .done(false)
                .createdAt(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build();
    }

    public static Task createOneWithIdNull() {
        return Task.builder()
                .id(null)
                .name("Task one")
                .done(false)
                .createdAt(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build();
    }

    public static Optional<Task> createOneOptional() {
        return Optional.of(createOne());
    }

    public static Set<Task> createListWithTwoItems() {
        return Set.of(
                createOne(),
                Task.builder()
                    .id(2L)
                    .name("Task two")
                    .done(false)
                    .createdAt(LocalDateTime.of(2021, 1, 1, 0, 0))
                    .build()
        );
    }

    public static Task create(Long id, String name, boolean done) {
        return Task.builder()
                .id(id)
                .name(name)
                .done(done)
                .build();
    }

    public static Task create(Long id, String name, boolean done, LocalDateTime createdAt) {
        return Task.builder()
                .id(id)
                .name(name)
                .done(done)
                .createdAt(createdAt)
                .build();
    }

    public static Set<Task> createMany(Task ... tasks) {
        return Set.of(tasks);
    }

}
