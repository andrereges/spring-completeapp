package br.com.andre.completeapp.integrations;

import br.com.andre.completeapp.dtos.TaskCreateRequestDto;
import br.com.andre.completeapp.dtos.TaskResponseDto;
import br.com.andre.completeapp.models.Task;
import br.com.andre.completeapp.models.User;
import br.com.andre.completeapp.repositories.TaskRepository;
import br.com.andre.completeapp.repositories.UserRepository;
import br.com.andre.completeapp.utils.TaskCreateDtoCreator;
import br.com.andre.completeapp.utils.TaskCreator;
import br.com.andre.completeapp.wrappers.PageableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskControllerIT {
    @Autowired
    @Qualifier(value = "testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private static final User USER = User.builder()
            .name("User System")
            .password("{bcrypt}$2a$10$xWiu45FDY8HlE1Tl2vLmt.f5w4Egnz2RRXUTQ3kmVkqM1XOowXCMS")
            .username("user")
            .authorities("ROLE_USER")
            .build();

    private static final User ADMIN = User.builder()
            .name("Admin System")
            .password("{bcrypt}$2a$10$xWiu45FDY8HlE1Tl2vLmt.f5w4Egnz2RRXUTQ3kmVkqM1XOowXCMS")
            .username("admin")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port + "/api/v1")
                    .basicAuthentication("user", "12345678");

            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port + "/api/v1")
                    .basicAuthentication("admin", "12345678");

            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list: returns list of task inside page object when successful")
    void test1() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(USER);

        String expectedName = savedTask.getName();

        PageableResponse<TaskResponseDto> taskDtoPage = testRestTemplateRoleUser.exchange("/tasks", HttpMethod.GET, null,
            new ParameterizedTypeReference<PageableResponse<TaskResponseDto>>() {
        }).getBody();

        assertThat(taskDtoPage).isNotNull();

        assertThat(taskDtoPage.toList())
                .isNotEmpty()
                .hasSize(1);

        assertThat(taskDtoPage.getContent().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById: returns task when successful")
    void test2() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(USER);

        Long expectedId = savedTask.getId();

        TaskResponseDto taskResponseDto = testRestTemplateRoleUser.getForObject("/tasks/{id}", TaskResponseDto.class, expectedId);

        assertThat(taskResponseDto).isNotNull();

        assertThat(taskResponseDto.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName: returns a pageable list of task when successful")
    void test3() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(USER);

        String expectedName = savedTask.getName();

        String url = String.format("/tasks/find-by-name?name=%s", expectedName);

        PageableResponse<TaskResponseDto> pageTasks = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
            new ParameterizedTypeReference<PageableResponse<TaskResponseDto>>() {
        }).getBody();

        assertThat(pageTasks.getContent())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(pageTasks.getContent().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName: returns a pageable empty list of task when task is not found")
    void test4() {
        userRepository.save(USER);

        PageableResponse<TaskResponseDto> pageTasks = testRestTemplateRoleUser.exchange("/tasks/find-by-name?name=empty",
                HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<TaskResponseDto>>() {}).getBody();

        assertThat(pageTasks)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save: returns task when successful")
    void test5() {
        userRepository.save(USER);

        TaskCreateRequestDto requestDto = TaskCreateDtoCreator.createOne();

        ResponseEntity<TaskResponseDto> taskResponseDto = testRestTemplateRoleUser
                .postForEntity("/tasks", requestDto, TaskResponseDto.class);

        assertThat(taskResponseDto).isNotNull();
        assertThat(taskResponseDto.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(taskResponseDto.getBody()).isNotNull();
        assertThat(taskResponseDto.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("update: updates task when successful")
    void test6() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(USER);

        savedTask.setName("new name");

        ResponseEntity<TaskResponseDto> taskResponseDto = testRestTemplateRoleUser.exchange("/tasks",
                HttpMethod.PUT, new HttpEntity<>(savedTask), TaskResponseDto.class);

        assertThat(taskResponseDto).isNotNull();

        assertThat(taskResponseDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("delete: removes task when successful")
    void test7() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(ADMIN);

        ResponseEntity<Void> taskResponseEntity = testRestTemplateRoleAdmin.exchange("/tasks/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedTask.getId());

        assertThat(taskResponseEntity).isNotNull();

        assertThat(taskResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    @DisplayName("delete: returns 403 when user is not admin")
    void test8() {
        Task savedTask = taskRepository.save(TaskCreator.createOne());
        userRepository.save(USER);

        ResponseEntity<Void> taskResponseEntity = testRestTemplateRoleUser.exchange("/tasks/admin/{id}",
                HttpMethod.DELETE, null, Void.class, savedTask.getId());

        assertThat(taskResponseEntity).isNotNull();

        assertThat(taskResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
