package br.com.andre.completeapp.models;

import br.com.andre.completeapp.utils.TaskCreator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class TaskTest {

    @Test
    @DisplayName("Task created with success")
    public void test1() {
        Task task = TaskCreator.createOne();

        assertEquals(1L, task.getId());
        assertEquals("Task one", task.getName());
        assertFalse(task.isDone());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), task.getCreatedAt());

    }

    @Test
    @DisplayName("Task to string with success")
    public void test2() {
        Task task = TaskCreator.createOne();
        String expected = String.format("Task(id=%s, name=%s)", task.getId(), task.getName());
        Assertions.assertEquals(expected, task.toString());
    }
}
