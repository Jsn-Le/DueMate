package com.duemate.duemate.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.duemate.duemate.dto.UserRequest;
import com.duemate.duemate.dto.UserResponse;
import com.duemate.duemate.exception.DuplicateUserException;
import com.duemate.duemate.exception.ForbiddenException;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final CurrentUserService currentUserService;

    // POST - Create a user
    public UserResponse createUser(UserRequest request) {
        if (isEmailTaken(request.getEmail())) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        User user = userMapper.convertRequestToUser(request);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return userMapper.convertUserTResponse(user);
    }

    // GET - Get a user by ID
    public UserResponse getUserById(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        User user = getUserEntityById(id);
        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        return userMapper.convertUserTResponse(user);
    }

    // UPDATE - Update a user
    public UserResponse updateUser(UserRequest request, Long id) {
        User currentUser = currentUserService.getCurrentUser();
        User user = getUserEntityById(id);
        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
        if (isEmailTakenByAnotherUser(request, id)) {
            throw new DuplicateUserException("A user with this email already exists.");
        }

        user.setEmail(request.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setDefaultCurrency(request.getDefaultCurrency());
        userRepository.save(user);

        return userMapper.convertUserTResponse(user);
    }

    // DELETE - Delete a user
    public String deleteUser(Long id) {
        User currentUser = currentUserService.getCurrentUser();
        User user = getUserEntityById(id);
        if (!currentUser.getEmail().equals(user.getEmail())) {
            throw new ForbiddenException("You do not have permission to access this resource");
        }
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
