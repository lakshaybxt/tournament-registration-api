package com.gametosa.tournament.service.impl;

import com.gametosa.tournament.domain.dto.LoginUserDto;
import com.gametosa.tournament.domain.dto.RegisterUserDto;
import com.gametosa.tournament.domain.dto.VerifyUserDto;
import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.repository.UserRepository;
import com.gametosa.tournament.service.AuthenticationService;
import com.gametosa.tournament.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public User signUp(RegisterUserDto request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .verificationCode(generateVerificationCode())
                .verificationCodeExpiration(LocalDateTime.now().plusMinutes(15))
                .build();

        User savedUser = userRepository.save(user);
        sendVerificationEmail(user);

        return savedUser;
    }

    private void sendVerificationEmail(User user) {
        String to = user.getEmail();
        String subject = "Verify your email";
        String verificationCode = "VERIFICATION CODE " + user.getVerificationCode();
        String htmlMessage = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <title>Verify Your Gamer Account</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "      background-color: #0d1117;\n" +
                "      color: #c9d1d9;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "    .container {\n" +
                "      max-width: 600px;\n" +
                "      background-color: #161b22;\n" +
                "      border: 2px solid #30363d;\n" +
                "      border-radius: 8px;\n" +
                "      padding: 30px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "    .logo {\n" +
                "      font-size: 24px;\n" +
                "      color: #58a6ff;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "    .code-box {\n" +
                "      font-size: 32px;\n" +
                "      color: #00ff88;\n" +
                "      background-color: #21262d;\n" +
                "      border: 2px dashed #00ff88;\n" +
                "      padding: 15px 0;\n" +
                "      margin: 20px auto;\n" +
                "      border-radius: 6px;\n" +
                "      width: 200px;\n" +
                "      letter-spacing: 4px;\n" +
                "    }\n" +
                "    .footer {\n" +
                "      margin-top: 30px;\n" +
                "      font-size: 12px;\n" +
                "      color: #8b949e;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div class=\"container\">\n" +
                "    <div class=\"logo\">\uD83C\uDFAE Gametosa Tournament</div>\n" +
                "    <h2>Ready Player One?</h2>\n" +
                "    <p>Welcome to the arena! Use the verification code below to complete your sign-up:</p>\n" +
                "\n" +
                "    <div class=\"code-box\">{{VERIFICATION_CODE}}</div>\n" +
                "\n" +
                "    <p>This code will expire in 10 minutes. Don't let the timer run out!</p>\n" +
                "    <p>Good luck and game on! \uD83D\uDD79\uFE0F</p>\n" +
                "\n" +
                "    <div class=\"footer\">\n" +
                "      &copy; 2025 Gametosa. All rights reserved.\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</body>\n" +
                "</html>\n";

        htmlMessage = htmlMessage.replace("{{VERIFICATION_CODE}}", user.getVerificationCode());

        try {
            emailService.sendVerificationEmail(to, subject, htmlMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    @Override
    public UserDetails authenticate(LoginUserDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with the email"));

        if(!user.isEnabled()) {
            throw new RuntimeException("Account not verified yet. Please verify your account");
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password");
        }
        return userDetailsService.loadUserByUsername(request.getEmail());
    }

    @Override
    public void verifyUser(VerifyUserDto request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.getVerificationCodeExpiration().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has been expired");
            }

            if(user.getVerificationCode().equals(request.getVerificationCode())) {
                user.setEnabled(true);
                user.setVerificationCode(null);
                user.setVerificationCodeExpiration(null);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void resendVerificationCode(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if(user.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }

            user.setVerificationCode(generateVerificationCode());
            user.setVerificationCodeExpiration(LocalDateTime.now().plusMinutes(15));

            userRepository.save(user);
            sendVerificationEmail(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
