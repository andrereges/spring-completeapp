package br.com.andre.completeapp.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskUpdateRequestDto {

    private Long id;
    private String name;
    private boolean done;
}
