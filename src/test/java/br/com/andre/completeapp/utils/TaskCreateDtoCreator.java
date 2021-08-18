package br.com.andre.completeapp.utils;

import br.com.andre.completeapp.dtos.TaskCreateRequestDto;

public class TaskCreateDtoCreator {
    public static TaskCreateRequestDto createOne() {
        return TaskCreateRequestDto.builder()
                .name("Task one")
                .done(false)
                .build();
    }

    public static TaskCreateRequestDto create(Long id, String name, boolean done) {
        return TaskCreateRequestDto.builder()
                .name(name)
                .done(done)
                .build();
    }

}
