package com.ushirikeduc.users.auth;

import com.ushirikeduc.users.config.JwtService;
import com.ushirikeduc.users.dtoRequests.*;
import com.ushirikeduc.users.model.Role;
import com.ushirikeduc.users.service.AuthenticationService;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/auth")
public record AuthenticationController(
        AuthenticationService authenticationService,
        JwtService jwtService
) {
    @CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @PostMapping("/admin")
    public AuthenticationResponseAdmin register(
            @RequestBody RegisterRequest request
    ) {

        return authenticationService.registerAdmin(request, Role.ADMIN);

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RefreshTokenRequest request
    ) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));

    }
//    @PostMapping("/validate")
//
//    public Boolean isTokenValid(@RequestParam("token") String token) {
//        log.info("checking current token :" + token);
//        return authenticationService.validateToken(token);
//    }
    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        jwtService.validateToken(token);
        return  "the token " + token + "is valid" ;

    }
}
