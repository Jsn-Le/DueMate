package com.duemate.duemate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duemate.duemate.dto.UserRequest;
import com.duemate.duemate.dto.UserResponse;
import com.duemate.duemate.exception.DuplicateUserException;
import com.duemate.duemate.exception.UserNotFoundException;
import com.duemate.duemate.mapper.UserMapper;
import com.duemate.duemate.model.User;
import com.duemate.duemate.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    // POST - Create a user
    public UserResponse createUser(UserRequest request)  {
        if (isEmailTaken(request.getEmail())) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        User user = userMapper.convertRequestToUser(request);
        userRepository.save(user);

        return userMapper.convertUserTResponse(user);
    }

    // GET - Get all users
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.convertUserListTResponse(users);
    }

    // GET - Get a user by ID
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        return userMapper.convertUserTResponse(user);
    }

    // UPDATE - Update a user
    public UserResponse updateUser(UserRequest request, Long id) {
        User user = getUserEntityById(id);
        
        if (isEmailTakenByAnotherUser(request, id)) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDefaultCurrency(request.getDefaultCurrency());
        userRepository.save(user);

        return userMapper.convertUserTResponse(user);
    }

    // DELETE - Delete a user
    public String deleteUser(Long id) {
        User user = getUserEntityById(id);
        userRepository.delete(user);
        return "Successfully deleted";
    }

    // Check for duplicate email
    private boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean isEmailTakenByAnotherUser(UserRequest request, Long id) {
        return userRepository.existsByEmailAndIdNot(request.getEmail(), id);
    }

    // Fetch User Entity (Private Helper Method)
    protected User getUserEntityById(Long id) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found."));
        return user;
    }

}
