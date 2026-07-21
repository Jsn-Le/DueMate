package com.duemate.duemate.service;

import java.util.Currency;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.duemate.duemate.exception.DuplicateUserException;
import com.duemate.duemate.exception.UserNotFoundException;
import com.duemate.duemate.model.User;
import com.duemate.duemate.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    // POST - Create a user
    public User createUser(String email, String password, Currency defaultCurrency)  {
        if (isEmailTaken(email, null)) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        User user = new User(email, password, defaultCurrency);
        return userRepository.save(user);
    }

    // GET - Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // GET - Get a user by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
    }

    // UPDATE - Update a user
    public User updateUser(Long id, String email, String password, Currency defaultCurrency) {
        if (isEmailTaken(email, id)) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        User user = getUserById(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setDefaultCurrency(defaultCurrency);
        return userRepository.save(user);
    }

    // DELETE - Delete a user
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    // Check for duplicate email
    private boolean isEmailTaken(String email, Long id) {
        List<User> users = getAllUsers();

        for (User user : users) {
            String userEmail = user.getEmail();
            long userId = user.getId();
            if (Objects.equals(userEmail, email)) {
                if (userId != id) {
                    return true;
                }
            }
        }

        return false;
    }

}
