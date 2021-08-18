package br.com.andre.completeapp.services;

import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {
    Page<Task> listAll(Pageable pageable);
    Page<Task> findByName(Pageable pageable, String name);
    Task findByIdOrThrowBadRequestException(long id);
    Task save(TaskCreateRequestDto request);
    Task update(TaskUpdateRequestDto request);
    void delete(long id);
}
