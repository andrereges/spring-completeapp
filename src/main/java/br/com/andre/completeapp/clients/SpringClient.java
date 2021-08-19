package br.com.andre.completeapp.clients;

import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskResponseDto;
import br.com.andre.completeapp.dtos.TaskUpdateRequestDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

import static java.lang.Thread.sleep;

@Log4j2
public class SpringClient {
    private final static String APP_URI = "http://localhost:8080/api/v1";

    public static void main(String[] args) {
        try {
            TaskCreateRequestDto newTask = TaskCreateRequestDto.builder().name("New task").build();

            ResponseEntity<TaskResponseDto> newTaskSaved = new RestTemplate().exchange(APP_URI + "/tasks",
                    HttpMethod.POST, new HttpEntity<>(newTask, createJsonHeader()),
                    TaskResponseDto.class);

            log.info("saved task {}", newTaskSaved);

            //        ResponseEntity<TaskResponseDto> entity = new RestTemplate()
            //                .getForEntity(APP_URI + "/tasks/{id}", TaskResponseDto.class, 1);

            //        TaskResponseDto object = new RestTemplate()
            //                .getForObject(APP_URI + "/tasks/{id}",  TaskResponseDto.class, 1);

            ResponseEntity<TaskResponseDto> taskDto = new RestTemplate().exchange(APP_URI + "/tasks/{id}",
                    HttpMethod.GET, new HttpEntity<>(null, createJsonHeader()),
                    TaskResponseDto.class, 1);

            log.info(taskDto);

            TaskUpdateRequestDto taskToBeUpdated = TaskUpdateRequestDto.builder()
                    .name("New Task Updated")
                    .done(true)
                    .build();

            ResponseEntity<TaskResponseDto> newTaskUpdated = new RestTemplate().exchange(APP_URI + "/tasks",
                    HttpMethod.PUT, new HttpEntity<>(newTaskSaved.getBody(), createJsonHeader()), TaskResponseDto.class);

            log.info(newTaskUpdated);

            ResponseEntity<Void> newTaskDeleted = new RestTemplate().exchange(APP_URI + "/tasks/admin/{id}",
                    HttpMethod.DELETE, new HttpEntity<>(null, createJsonHeader()), Void.class, newTaskUpdated.getBody().getId());

            log.info(newTaskDeleted);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBasicAuth("admin", "12345678");

        return httpHeaders;
    }
}
