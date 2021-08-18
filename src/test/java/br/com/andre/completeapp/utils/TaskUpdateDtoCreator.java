package br.com.andre.completeapp.utils;

import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;

public class TaskUpdateDtoCreator {
    public static TaskUpdateRequestDto updateOne() {
        return TaskUpdateRequestDto.builder()
                .id(1L)
                .name("Task updated")
                .done(false)
                .build();
    }

}
