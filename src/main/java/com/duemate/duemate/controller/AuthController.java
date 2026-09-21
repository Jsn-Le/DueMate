package com.duemate.duemate.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duemate.duemate.dto.LoginRequest;
import com.duemate.duemate.service.AuthService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/login")
public class AuthController {

    private final AuthService authService;

    @PostMapping
    public String authenticateAndCreateToken(@RequestBody LoginRequest request) {
        return authService.authenticateAndCreateToken(request);
    }

}
