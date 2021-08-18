package br.com.andre.completeapp.mappers;

import br.com.andre.completeapp.domains.Task;
import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskResponseDto;
import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    public abstract Task toTask(TaskCreateRequestDto requestDto);

    public abstract Task toTask(TaskUpdateRequestDto requestDto);

    public abstract TaskResponseDto toTaskDto(Task task);

    public Page<TaskResponseDto> toDtoPage(Page<Task> tasks) {
        return tasks.map(this::toTaskDto);
    }

}
