package br.com.andre.completeapp.controllers;

import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskResponseDto;
import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;
import br.com.andre.completeapp.mappers.TaskMapper;
import br.com.andre.completeapp.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
@Log4j2
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(
        summary = "List all tasks paginated",
        description = "The default size is 20, use the parameter size to change the default value",
        tags = {"Task"}
    )
    public ResponseEntity<?> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(
            TaskMapper.INSTANCE.toDtoPage(taskService.listAll(pageable))
        );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskResponseDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(
            TaskMapper.INSTANCE.toTaskDto(taskService.findByIdOrThrowBadRequestException(id))
        );
    }

    @GetMapping(path = "/find-by-name")
    public ResponseEntity<?> findByName(@ParameterObject Pageable pageable, @RequestParam String name) {
        return ResponseEntity.ok(
            TaskMapper.INSTANCE.toDtoPage(taskService.findByName(pageable, name))
        );
    }

    @PostMapping
    public ResponseEntity<TaskResponseDto> save(@RequestBody @Valid TaskCreateRequestDto requestDto) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(TaskMapper.INSTANCE.toTaskDto(taskService.save(requestDto)));
    }

    @PutMapping
    public ResponseEntity<TaskResponseDto> update(@RequestBody TaskUpdateRequestDto requestDto) {
        return ResponseEntity.ok(
            TaskMapper.INSTANCE.toTaskDto(taskService.update(requestDto))
        );
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful Operation"),
        @ApiResponse(responseCode = "400", description = "When task not exists in database"),
        @ApiResponse(responseCode = "403", description = "Logged user doesn't authorization to execute this action")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        taskService.delete(id);

        return ResponseEntity.ok().build();
    }
}
