package com.duemate.duemate.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.duemate.duemate.model.User;
import com.duemate.duemate.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor 
@Service 
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.getUserByEmail(email);
    }

}
