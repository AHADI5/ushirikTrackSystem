package com.ushirikeduc.users.auth;

import com.ushirikeduc.users.dtoRequests.AuthenticationRequest;
import com.ushirikeduc.users.dtoRequests.AuthenticationResponse;
import com.ushirikeduc.users.dtoRequests.RefreshTokenRequest;
import com.ushirikeduc.users.dtoRequests.RegisterRequest;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public record AuthenticationController(
        AuthenticationService authenticationService
) {
    @PostMapping("/admin")
    public void register(
            @RequestBody RegisterRequest request
    ){

        authenticationService.registerAdmin(request, Role.ADMIN);

    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return  ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RefreshTokenRequest request
    ){
        return  ResponseEntity.ok(authenticationService.refreshToken(request));

    }
}
