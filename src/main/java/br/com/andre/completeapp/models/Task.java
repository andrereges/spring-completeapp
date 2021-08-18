package br.com.andre.completeapp.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id", "name", "done", "createAt"})
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "The task name cannot be empty")
    private String name;

    @Builder.Default
    private boolean done = false;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    public Task(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("The task name cannot be empty");
    }

    public void setName(String name) {
        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("The task name cannot be empty");
    }

}
