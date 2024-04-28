package com.ushirikeduc.users.service;

import com.ushirikeduc.users.config.JwtService;
import com.ushirikeduc.users.dtoRequests.*;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.model.Users;
import com.ushirikeduc.users.repository.UserRepository;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
                .enabled(true)
                .lastName(request.getLastName())
                .email(request.getEmail())
                .schoolID(request.getSchoolID())
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
        //Checking whether the user Exists
        Optional<Users> existingUser = userRepository.findByEmail(request.getEmail());

        Users user = Users.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .schoolID(request.getSchoolID())
                .email(request.getEmail())
                .enabled(true)
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

    public List<Users> getUsersBySchoolID(int schoolID) {
        return userRepository.findUsersBySchoolID(schoolID);
    }

    public List<Users> getUserParentBySchoolID(int schoolID, Role role) {
        return  userRepository.findUsersBySchoolIDAndRole(schoolID , role);
    }
    public List<Users> getUserTeacherBySchoolID(int schoolID, Role role) {
        return  userRepository.findUsersBySchoolIDAndRole(schoolID , role);
    }

    //Logic to activate or disable a user account
    public ResponseEntity<Boolean> disableUser (String userName)  {
        Users user  = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        if (user.isEnabled()) {
            user.setEnabled(false);
            userRepository.save(user);
            return ResponseEntity.ok(user.isEnabled());
        }

        return null;
    }

    public ResponseEntity<Boolean> enableUser (String userName) {
        Users user  = userRepository.findByEmail(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found !!"));
        if (!user.isEnabled()) {
            user.setEnabled(true);
            userRepository.save(user);
            return ResponseEntity.ok(user.isEnabled());
        }
        return null;
    }
}
