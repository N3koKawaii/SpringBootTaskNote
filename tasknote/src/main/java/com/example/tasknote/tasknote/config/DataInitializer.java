package com.example.tasknote.tasknote.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Add user
        AppUser admin = createUser("admin@gmail.com", passwordEncoder.encode("admin123"), Role.ADMIN, "admin");
        AppUser user1 = createUser("neko@gmail.com", passwordEncoder.encode("neko123"), Role.USER, "neko");
        AppUser user2 = createUser("neko2@gmail.com", passwordEncoder.encode("neko1234"), Role.USER, "neko2");

        // Add Admin Todo
        Todo adminTodo1 = createTodo("Admin Todo", "This is admin's todo", admin);
        Todo adminTodo2 = createTodo("Admin Second Todo", "This is admin's second todo", admin);
        Todo adminTodo3 = createTodo("Admin Third Todo", "This is admin's third todo", admin);

        // Add Todo
        Todo todo1 = createTodo("First Todo", "This is the first todo", user1);
        Todo todo2 = createTodo("Second Todo", "This is the second todo", user2);
        Todo todo3 = createTodo("Third Todo", "This is the third todo", user1);
        Todo todo4 = createTodo("Fourth Todo", "This is the fourth todo", user2);
        Todo todo5 = createTodo("Fifth Todo", "This is the fifth todo", user1);
        Todo todo6 = createTodo("Sixth Todo", "This is the sixth todo", user2);
        // Add Note
        Note note1 = createNote("First Note", "This is the first note", null, NoteType.STANDALONE, user1);
        Note note2 = createNote("Second Note", "This is the second note", null, NoteType.STANDALONE, user2);
        Note note3 = createNote("Third Note", "This is the third note", null, NoteType.STANDALONE, user1);
        Note note4 = createNote("Fourth Note", "This is the fourth note", todo1, NoteType.TODO_NOTE, user2);
        Note note5 = createNote("Fifth Note", "This is the fifth note", todo1, NoteType.TODO_NOTE, user1);
        Note note6 = createNote("Sixth Note", "This is the sixth note", todo3, NoteType.TODO_NOTE, user1);
        Note note7 = createNote("Seventh Note", "This is the seventh note", todo2, NoteType.TODO_NOTE, user2);
        Note note8 = createNote("Eighth Note", "This is the eighth note", todo2, NoteType.TODO_NOTE, user2);
        Note note9 = createNote("Ninth Note", "This is the ninth note", todo4, NoteType.TODO_NOTE, user2);

        userRepository.saveAll(List.of( admin, user1, user2 ));
        todoRepository.saveAll(List.of( adminTodo1, adminTodo2, adminTodo3 ));
        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5, todo6));
        noteRepository.saveAll(List.of( note1, note2, note3, note4, note5, note6, note7, note8, note9 ));


    }

    public AppUser createUser(String email, String password, Role role, String username){
        AppUser user = new AppUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        user.setUsername(username);
        return user;
    }

    public Todo createTodo(String title, String description, AppUser user){
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setUser(user);
        return todo;
    }

    public Note createNote(String title, String description, Todo todo, NoteType type, AppUser user){
        Note note = new Note();
        note.setTitle(title);
        note.setDescription(description);
        note.setTodo(todo);
        note.setType(type);
        note.setUser(user);
        return note;
    }
}
