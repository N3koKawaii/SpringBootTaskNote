package com.example.tasknote.tasknote.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.Priority;
import com.example.tasknote.tasknote.model_enum.Status;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query("""
        SELECT DISTINCT t
        FROM Todo t
        LEFT JOIN FETCH t.notes
        WHERE t.user = :user
    """)
    List<Todo> findAllByUserWithNotes(@Param("user") AppUser user);

    @Query("""
        SELECT DISTINCT t
        FROM Todo t
        LEFT JOIN FETCH t.notes
        WHERE t.user = :user AND t.status = :status
    """)
    List<Todo> findAllByUserAndStatusWithNotes(
            @Param("user") AppUser user,
            @Param("status") Status status
    );

    @Query("""
        SELECT DISTINCT t
        FROM Todo t
        LEFT JOIN FETCH t.notes
        WHERE t.user = :user AND t.priority = :priority
    """)
    List<Todo> findAllByUserAndPriorityWithNotes(
            @Param("user") AppUser user,
            @Param("priority") Priority priority
    );

    @Query("""
        SELECT t
        FROM Todo t
        LEFT JOIN FETCH t.notes
        WHERE t.id = :id AND t.user = :user
    """)
    Optional<Todo> findByIdAndUserWithNotes(
            @Param("id") Long id,
            @Param("user") AppUser user
    );

    // lightweight queries 
    List<Todo> findAllByUser(AppUser user);
    List<Todo> findAllByUserAndStatus(AppUser user, Status status);
    List<Todo> findAllByUserAndPriority(AppUser user, Priority priority);
    Optional<Todo> findByIdAndUser(Long id, AppUser user);
    boolean existsByIdAndUser(Long id, AppUser user);
}
