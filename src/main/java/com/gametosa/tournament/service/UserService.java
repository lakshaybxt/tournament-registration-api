package com.gametosa.tournament.service;


import com.gametosa.tournament.domain.entity.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID userId);
}
