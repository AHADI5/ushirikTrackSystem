package com.ushirikeduc.users.service;

import com.ushirikeduc.users.dtoRequests.AuthenticationRequest;
import com.ushirikeduc.users.dtoRequests.AuthenticationResponse;
import com.ushirikeduc.users.dtoRequests.RefreshTokenRequest;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.config.JwtService;
import com.ushirikeduc.users.model.Role;

import com.ushirikeduc.users.model.Users;
import com.ushirikeduc.users.repository.UserRepository;
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

    public void registerAdmin(RegisterRequest request, Role role) {
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
}
