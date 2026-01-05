package com.example.tasknote.tasknote.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Note;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.NoteType;
import com.example.tasknote.tasknote.model_enum.Role;
import com.example.tasknote.tasknote.repository.NoteRepository;
import com.example.tasknote.tasknote.repository.TodoRepository;
import com.example.tasknote.tasknote.repository.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner{

    @Autowired
    public UserRepository userRepository;
    
    @Autowired
    public TodoRepository todoRepository;

    @Autowired
    public NoteRepository noteRepository;

    @Override
    public void run(String... args) throws Exception {
        // Add user
        AppUser admin = new AppUser("admin@gmail.com", "admin123", Role.ROLE_ADMIN, "admin");
        AppUser user1 = new AppUser("neko@gmail.com", "neko123", Role.ROLE_USER, "neko");
        AppUser user2 = new AppUser("neko2@gmail.com", "neko1234", Role.ROLE_USER, "neko2");

        // Add Todo
        Todo todo1 = new Todo("First Todo", "This is the first todo", user1);
        Todo todo2 = new Todo("Second Todo", "This is the second todo", user2);
        Todo todo3 = new Todo("Third Todo", "This is the third todo", user1);
        Todo todo4 = new Todo("Fourth Todo", "This is the fourth todo", user2);
        Todo todo5 = new Todo("Fifth Todo", "This is the fifth todo", user1);
        Todo todo6 = new Todo("Sixth Todo", "This is the sixth todo", user2);

        // Add Note
        Note note1 = new Note("First Note", "This is the first note", null, NoteType.STANDALONE, user1);
        Note note2 = new Note("Second Note", "This is the second note", null, NoteType.STANDALONE, user2);
        Note note3 = new Note("Third Note", "This is the third note", null, NoteType.STANDALONE, user1);
        Note note4 = new Note("Fourth Note", "This is the fourth note", todo1, NoteType.TODO_NOTE, user2);
        Note note5 = new Note("Fifth Note", "This is the fifth note", todo1, NoteType.TODO_NOTE, user1);
        Note note6 = new Note("Sixth Note", "This is the sixth note", todo3, NoteType.TODO_NOTE, user1);
        Note note7 = new Note("Seventh Note", "This is the seventh note", todo2, NoteType.TODO_NOTE, user2);
        Note note8 = new Note("Eighth Note", "This is the eighth note", todo2, NoteType.TODO_NOTE, user2);
        Note note9 = new Note("Ninth Note", "This is the ninth note", todo4, NoteType.TODO_NOTE, user2);

        userRepository.saveAll(List.of( admin, user1, user2 ));
        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5, todo6));
        noteRepository.saveAll(List.of( note1, note2, note3, note4, note5, note6, note7, note8, note9 ));


    }
}
