package com.example.tasknote.tasknote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Note;
import com.example.tasknote.tasknote.model.Todo;

public interface NoteRepository extends JpaRepository<Note, Long>{
    List<Note> findAllByUser(AppUser user);

    List<Note> findAllByUserAndTodo(AppUser user, Todo todo);

    Optional<Note> findByIdAndUser(Long id, AppUser user);
}
