package br.com.andre.completeapp.utils;

import br.com.andre.completeapp.dtos.TaskResponseDto;

import java.time.LocalDateTime;

public class TaskResponseDtoCreator {
    public static TaskResponseDto createOne() {
        return TaskResponseDto.builder()
                .id(1L)
                .name("Task one")
                .done(false)
                .createdAt(LocalDateTime.of(2021, 1, 1, 0, 0))
                .build();
    }

    public static TaskResponseDto create(Long id, String name, boolean done, LocalDateTime createdAt) {
        return TaskResponseDto.builder()
                .id(1L)
                .name(name)
                .done(done)
                .createdAt(createdAt)
                .build();
    }

}
