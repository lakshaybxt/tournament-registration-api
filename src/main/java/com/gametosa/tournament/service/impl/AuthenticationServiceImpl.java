package com.gametosa.tournament.service.impl;

import com.gametosa.tournament.domain.dto.LoginUserDto;
import com.gametosa.tournament.domain.dto.RegisterUserDto;
import com.gametosa.tournament.domain.dto.VerifyUserDto;
import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Override
    public User signUp(RegisterUserDto request) {
        return null;
    }

    @Override
    public UserDetails authenticate(LoginUserDto request) {
        return null;
    }

    @Override
    public void verifyUser(VerifyUserDto request) {

    }

    @Override
    public void resendVerificationCode(String email) {

    }
}
