package com.gametosa.tournament.service;

import com.gametosa.tournament.domain.dto.LoginUserDto;
import com.gametosa.tournament.domain.dto.RegisterUserDto;
import com.gametosa.tournament.domain.dto.VerifyUserDto;
import com.gametosa.tournament.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    User signUp(RegisterUserDto request);
    UserDetails authenticate(LoginUserDto request);
    void verifyUser(VerifyUserDto request);
    void resendVerificationCode(String email);
}
