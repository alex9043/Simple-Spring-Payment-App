package ru.alex9043.simplespringpaymentapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.simplespringpaymentapp.dao.error.ErrorResponse;
import ru.alex9043.simplespringpaymentapp.dao.user.AuthenticationRequest;
import ru.alex9043.simplespringpaymentapp.dao.user.AuthenticationResponse;
import ru.alex9043.simplespringpaymentapp.error.AuthenticationException;
import ru.alex9043.simplespringpaymentapp.service.AuthenticationService;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
}
