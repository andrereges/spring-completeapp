package br.com.andre.completeapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "This is the task's name", example = "Buy bread at the bakery", required = true)
    private String name;

    @Schema(description = "Use TRUE if task is complete")
    private boolean done;
}
