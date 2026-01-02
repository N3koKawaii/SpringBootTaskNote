package com.example.tasknote.tasknote.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model.Todo;
import com.example.tasknote.tasknote.model_enum.Role;
import com.example.tasknote.tasknote.repository.TodoRepository;
import com.example.tasknote.tasknote.repository.UserRepository;

@Configuration
public class DataInitializer implements CommandLineRunner{

    @Autowired
    public UserRepository userRepository;
    
    @Autowired
    public TodoRepository todoRepository;

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

        userRepository.saveAll(List.of( admin, user1, user2 ));
        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4, todo5, todo6));

    }
}
