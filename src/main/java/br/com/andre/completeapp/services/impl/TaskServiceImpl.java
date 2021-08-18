package br.com.andre.completeapp.services.impl;

import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;
import br.com.andre.completeapp.exceptions.BadRequestException;
import br.com.andre.completeapp.mappers.TaskMapper;
import br.com.andre.completeapp.repositories.TaskRepository;
import br.com.andre.completeapp.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public Page<Task> listAll(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<Task> findByName(Pageable pageable, String name) {
        return taskRepository.findByNameContainingIgnoreCase(pageable, name);
    }

    @Override
    public Task findByIdOrThrowBadRequestException(long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Task", id));
    }

    @Override
    @Transactional
    public Task save(TaskCreateRequestDto requestDto) {
        return taskRepository.save(TaskMapper.INSTANCE.toTask(requestDto));
    }

    @Override
    @Transactional
    public Task update(TaskUpdateRequestDto requestDto) {
        Task savedTask = findByIdOrThrowBadRequestException(requestDto.getId());
        Task task = TaskMapper.INSTANCE.toTask(requestDto);
        task.setId(savedTask.getId());

        return taskRepository.save(task);
    }

    @Override
    public void delete(long id) {
        taskRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
