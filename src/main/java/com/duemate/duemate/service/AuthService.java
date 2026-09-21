package com.duemate.duemate.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.duemate.duemate.dto.LoginRequest;
import com.duemate.duemate.security.JwtService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String authenticateAndCreateToken(LoginRequest loginRequest) {

        String userEmail = loginRequest.getEmail();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userEmail,
                loginRequest.getPassword(), null);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        String jwt = jwtService.createJWT((UserDetails) authentication.getPrincipal());

        return jwt;
    }

}
