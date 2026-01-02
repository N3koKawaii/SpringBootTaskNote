package com.example.tasknote.tasknote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.Priority;
import com.example.tasknote.tasknote.model_enum.Status;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUser(AppUser user);

    List<Todo> findAllByUserAndStatus(AppUser user, Status status);

    List<Todo> findAllByUserAndPriority(AppUser user, Priority priority);

    Optional<Todo> findByIdAndUser(Long id, AppUser user);
}
