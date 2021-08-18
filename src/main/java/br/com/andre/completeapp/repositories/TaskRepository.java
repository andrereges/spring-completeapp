package br.com.andre.completeapp.repositories;

import br.com.andre.completeapp.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
