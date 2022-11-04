package com.kms.seft203.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAll();
    Optional<Task> findTaskById(Long id);
    @Query("Select t from Task t join User u  on u.id = :id where t.isCompleted = true")
    List<Task> findAllCompletion(Long id);
}
