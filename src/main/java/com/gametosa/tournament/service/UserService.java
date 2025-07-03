package com.gametosa.tournament.service;


import com.gametosa.tournament.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
    User findByEmail(String email);
    List<User> findAll();
}
