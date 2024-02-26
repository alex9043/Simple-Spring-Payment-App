package ru.alex9043.simplespringpaymentapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex9043.simplespringpaymentapp.dao.user.AuthenticationRequest;
import ru.alex9043.simplespringpaymentapp.dao.user.AuthenticationResponse;
import ru.alex9043.simplespringpaymentapp.dao.user.UserResponse;
import ru.alex9043.simplespringpaymentapp.domain.User;
import ru.alex9043.simplespringpaymentapp.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        repository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(UserResponse.builder()
                        .username(user.getUsername())
                        .build())
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        var user = repository.findByUsername(request.getUsername())
                .orElseThrow(); // TODO throw custom exception
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .user(UserResponse.builder()
                        .username(user.getUsername())
                        .build())
                .token(token)
                .build();
    }
}
