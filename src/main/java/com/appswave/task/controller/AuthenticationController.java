package com.appswave.task.controller;

import com.appswave.task.common.security.JwtService;
import com.appswave.task.model.dtos.UserDTO;
import com.appswave.task.model.request.AuthenticationRequest;
import com.appswave.task.model.response.AuthenticationResponse;
import com.appswave.task.service.UserDetailsServiceImpl;
import com.appswave.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/generate-token")
    public ResponseEntity<AuthenticationResponse> authenticateAndGetToken(
            @RequestBody AuthenticationRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getEmail());
            AuthenticationResponse response = new AuthenticationResponse(token);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (!jwtService.isTokenExpired(token) && !jwtService.isTokenInvalidated(token)) {
                String refreshedToken = jwtService.refreshToken(token);
                return ResponseEntity.ok(Collections.singletonMap("token", refreshedToken));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        jwtService.invalidateToken(token);
        return ResponseEntity.ok().build();
    }
}

