package com.gametosa.tournament.controller;

import com.gametosa.tournament.domain.dto.LoginUserDto;
import com.gametosa.tournament.domain.dto.RegisterUserDto;
import com.gametosa.tournament.domain.dto.VerifyUserDto;
import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.response.LoginResponse;
import com.gametosa.tournament.service.AuthenticationService;
import com.gametosa.tournament.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping(path = "/signup")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signUp(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginUserDto request) {
        UserDetails userDetails = authenticationService.authenticate(request);
        String jwtToken = jwtService.generateToken(userDetails);
        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiration(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<?> verifyUser(@Valid @RequestBody VerifyUserDto request) {
        try {
            authenticationService.verifyUser(request);
            return ResponseEntity.ok("Account verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(path = "/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification code sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
