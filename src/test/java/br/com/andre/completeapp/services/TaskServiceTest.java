package br.com.andre.completeapp.services;

import br.com.andre.completeapp.exceptions.BadRequestException;
import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.repositories.TaskRepository;
import br.com.andre.completeapp.services.impl.TaskServiceImpl;
import br.com.andre.completeapp.utils.TaskCreateDtoCreator;
import br.com.andre.completeapp.utils.TaskCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(SpringExtension.class)
public class TaskServiceTest {
    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> taskPage = new PageImpl<>(List.of(TaskCreator.createOne()));
        BDDMockito.when(taskRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(taskPage);

        BDDMockito.when(taskRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(taskPage);

        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(TaskCreator.createOne()));

        BDDMockito.when(taskRepositoryMock
                .findByNameContainingIgnoreCase(ArgumentMatchers.any(PageRequest.class), ArgumentMatchers.anyString()))
                .thenReturn(taskPage);

        BDDMockito.when(taskRepositoryMock.save(ArgumentMatchers.any(Task.class)))
                .thenReturn(TaskCreator.createOne());

        BDDMockito.doNothing().when(taskRepositoryMock).delete(ArgumentMatchers.any(Task.class));
    }

    @Test
    @DisplayName("listAll: returns list of task inside page object when successful")
    void test1(){
        String expectedName = TaskCreator.createOne().getName();

        Page<Task> taskPage = taskService.listAll(PageRequest.ofSize(20));

        Assertions.assertThat(taskPage).isNotNull();

        Assertions.assertThat(taskPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(taskPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException: returns task when successful")
    void test2(){
        Long expectedId = TaskCreator.createOne().getId();

        Task task = taskService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(task).isNotNull();

        Assertions.assertThat(task.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException: throws BadRequestException when task is not found")
    void test3(){
        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> taskService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("findByName: returns a list of task when successful")
    void test4(){
        String expectedName = TaskCreator.createOne().getName();

        Page<Task> tasks = taskService.findByName(PageRequest.ofSize(20), "one");

        Assertions.assertThat(tasks.getContent())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(tasks.getContent().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns an empty list of task when task is not found")
    void test5(){
        BDDMockito.when(taskRepositoryMock
            .findByNameContainingIgnoreCase(PageRequest.ofSize(20), "empty"))
            .thenReturn(Page.empty());

        Page<Task> tasks = taskService.findByName(PageRequest.ofSize(20), "empty");

        Assertions.assertThat(tasks.getContent())
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save: returns task when successful")
    void test6(){

        Task task = taskService.save(TaskCreateDtoCreator.createOne());

        Assertions.assertThat(task).isNotNull().isEqualTo(TaskCreator.createOne());

    }

    @Test
    @DisplayName("update: updates task when successful")
    void test7(){
        assertThatCode(() -> taskService.update(TaskUpdateDtoCreator.updateOne()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete: removes task when successful")
    void test8(){
        assertThatCode(() -> taskService.delete(1L))
                .doesNotThrowAnyException();
    }
}
