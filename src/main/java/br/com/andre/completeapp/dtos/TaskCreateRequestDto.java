package br.com.andre.completeapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequestDto {

    @NotEmpty(message = "The task name cannot be empty")
    private String name;
    private boolean done;
}
