package br.com.andre.completeapp.repositories;

import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.utils.TaskCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DisplayName("Tests for Task Repository")
@Log4j2
public class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Save persists task when Successful")
    void test1(){
        Task taskToBeSaved = TaskCreator.createOneWithIdNull();

        Task taskSaved = this.taskRepository.save(taskToBeSaved);

        assertThat(taskSaved).isNotNull();

        assertThat(taskSaved.getId()).isNotNull();

        assertThat(taskSaved.getName()).isEqualTo(taskToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates task when Successful")
    void test2(){
        Task taskToBeSaved = TaskCreator.createOneWithIdNull();

        Task taskSaved = this.taskRepository.save(taskToBeSaved);

        taskSaved.setName("Task updated");
        taskSaved.setDone(true);
        taskSaved.setCreatedAt(LocalDateTime.of(2022, 2, 2, 2, 2));

        Task taskUpdated = this.taskRepository.save(taskSaved);

        assertThat(taskUpdated).isNotNull();

        assertThat(taskUpdated.getId()).isNotNull();

        assertThat(taskUpdated.getName()).isEqualTo(taskSaved.getName());
        assertThat(taskUpdated.isDone()).isEqualTo(taskSaved.isDone());
        assertThat(taskUpdated.getCreatedAt()).isEqualTo(taskSaved.getCreatedAt());
    }

    @Test
    @DisplayName("Delete removes task when Successful")
    void test3(){
        Task taskToBeSaved = TaskCreator.createOneWithIdNull();

        Task taskSaved = this.taskRepository.save(taskToBeSaved);

        this.taskRepository.delete(taskSaved);

        Optional<Task> taskOptional = this.taskRepository.findById(taskSaved.getId());

        assertThat(taskOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Name returns list of task when Successful")
    void test4(){
        Task taskToBeSaved = TaskCreator.createOneWithIdNull();

        Task taskSaved = this.taskRepository.save(taskToBeSaved);

        String name = taskSaved.getName();

        Page<Task> tasks = this.taskRepository.findByNameContainingIgnoreCase(Pageable.unpaged(), name);

        assertThat(tasks.getContent())
                .isNotEmpty()
                .contains(taskSaved);

    }

    @Test
    @DisplayName("Find By Name returns empty list when no task is found")
    void test5(){
        Page<Task> tasks = this.taskRepository.findByNameContainingIgnoreCase(Pageable.unpaged(),"task three");

        assertThat(tasks.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void test6(){
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.taskRepository.save(new Task()))
                .withMessageContaining("The task name cannot be empty");
    }
}
