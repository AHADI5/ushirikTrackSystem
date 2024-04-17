package com.ushirikeduc.users.service;

import com.ushirikeduc.users.config.JwtService;
import com.ushirikeduc.users.dtoRequests.*;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.model.Users;
import com.ushirikeduc.users.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public record AuthenticationService(
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        UserRepository userRepository
) {
    public void register(RegisterRequest request, Role role) {
        Users user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
//        var jwToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwToken)
//                .build();

    }

    public AuthenticationResponseAdmin registerAdmin(RegisterRequest request, Role role) {
        Users user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();
        Users savedUser = userRepository.save(user);
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponseAdmin.builder()
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .email(savedUser.getEmail())
                .token(jwToken)
                .build();

    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<Users> user = userRepository.findByEmail(request.getEmail());
        var jwToken = jwtService.generateToken(user.get());
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user.get());
        return AuthenticationResponse.builder()
                .token(jwToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshToken) {
        String userEmail = jwtService.extractUsername(refreshToken.getToken());
        Users user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshToken.getToken(), user)) {
            var jwToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwToken)
                    .build();
        }
        return null;
    }

    //validate token

    public Boolean validateToken(String token) {
        String userEmail = jwtService().extractUsername(token);
        Users user = userRepository.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException("User Not found"));
        return jwtService().isTokenValid(token , user);
    }
}
