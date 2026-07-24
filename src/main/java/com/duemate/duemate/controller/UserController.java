package com.duemate.duemate.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duemate.duemate.model.User;
import com.duemate.duemate.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // POST - Create a user
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user.getEmail(), user.getPassword(), user.getDefaultCurrency());
    }

    // GET - Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // GET - Get a user by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // UPDATE - Update a user
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user.getEmail(), user.getPassword(), user.getDefaultCurrency());
    }

    @DeleteMapping("/{id}")
    // DELETE - Delete a user
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
