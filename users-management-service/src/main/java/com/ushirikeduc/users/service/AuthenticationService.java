package com.ushirikeduc.users.service;

import com.ushirikeduc.users.auth.AuthenticationRequest;
import com.ushirikeduc.users.auth.AuthenticationResponse;
import com.ushirikeduc.users.auth.RegisterRequest;
import com.ushirikeduc.users.config.JwtService;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.model.User;
import com.ushirikeduc.users.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public record AuthenticationService(
        PasswordEncoder passwordEncoder,
        JwtService jwtService,
        AuthenticationManager authenticationManager,
        UserRepository userRepository
) {
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.PARENT)
                .build();
        var jwToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwToken)
                .build();

                //todo save user




    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        var jwToken = jwtService.generateToken(user.get());
                return AuthenticationResponse.builder()
                        .token(jwToken)
                        .build();
    }
}
