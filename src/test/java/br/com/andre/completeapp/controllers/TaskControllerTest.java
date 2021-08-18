package br.com.andre.completeapp.controllers;

import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskResponseDto;
import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.services.impl.TaskServiceImpl;
import br.com.andre.completeapp.utils.TaskCreateDtoCreator;
import br.com.andre.completeapp.utils.TaskCreator;
import br.com.andre.completeapp.utils.TaskResponseDtoCreator;
import br.com.andre.completeapp.utils.TaskUpdateDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskServiceImpl taskServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> taskPage = new PageImpl<>(List.of(TaskCreator.createOne()));
        BDDMockito.when(taskServiceMock.listAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(taskPage);

        BDDMockito.when(taskServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(TaskCreator.createOne());

        BDDMockito.when(taskServiceMock
                        .findByName(ArgumentMatchers.any(PageRequest.class), ArgumentMatchers.anyString()))
                .thenReturn(taskPage);

        BDDMockito.when(taskServiceMock.save(ArgumentMatchers.any(TaskCreateRequestDto.class)))
                .thenReturn(TaskCreator.createOne());

        BDDMockito.doNothing().when(taskServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list: returns list of task inside page object when successful")
    void test1(){
        String expectedName = TaskCreator.createOne().getName();

        Page<TaskResponseDto> taskPage = (Page<TaskResponseDto>)  taskController
                .list(Pageable.ofSize(20)).getBody();

        Assertions.assertThat(taskPage).isNotNull();

        Assertions.assertThat(taskPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(taskPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById: returns task when successful")
    void test2(){
        Long expectedId = TaskCreator.createOne().getId();

        TaskResponseDto taskDto = taskController.findById(1).getBody();

        Assertions.assertThat(taskDto).isNotNull();

        Assertions.assertThat(taskDto.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a list of task when successful")
    void test3(){
        String expectedName = TaskCreator.createOne().getName();

        Page<TaskResponseDto> pageTasks = (Page<TaskResponseDto>) taskController
                .findByName(PageRequest.ofSize(20), "task").getBody();

        Assertions.assertThat(pageTasks.getContent())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(pageTasks.getContent().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list of task when task is not found")
    void test4(){
        BDDMockito.when(taskServiceMock.findByName(PageRequest.ofSize(20), "empty"))
                .thenReturn(Page.empty());

        Page<TaskResponseDto> tasks = (Page<TaskResponseDto>) taskController
                .findByName(PageRequest.ofSize(20), "empty").getBody();

        Assertions.assertThat(tasks)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save: returns task when successful")
    void test5(){
        TaskResponseDto taskDto = taskController.save(TaskCreateDtoCreator.createOne()).getBody();

        Assertions.assertThat(taskDto).isNotNull().isEqualTo(TaskResponseDtoCreator.createOne());
    }

    @Test
    @DisplayName("update: updates task when successful")
    void test6(){
        Assertions.assertThatCode(() ->taskController.update(TaskUpdateDtoCreator.updateOne()))
                .doesNotThrowAnyException();

        ResponseEntity<TaskResponseDto> taskDto = taskController.update(TaskUpdateDtoCreator.updateOne());

        Assertions.assertThat(taskDto).isNotNull();

        Assertions.assertThat(taskDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete: removes task when successful")
    void test7(){

        Assertions.assertThatCode(() ->taskController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = taskController.delete(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
