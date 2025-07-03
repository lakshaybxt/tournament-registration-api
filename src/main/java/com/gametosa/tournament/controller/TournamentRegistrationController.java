package com.gametosa.tournament.controller;

import com.gametosa.tournament.domain.dto.TournamentRegisterRequest;
import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.repository.UserRepository;
import com.gametosa.tournament.service.TournamentRegistrationService;
import com.gametosa.tournament.service.TournamentService;
import com.gametosa.tournament.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class TournamentRegistrationController {

    private final TournamentRegistrationService registrationService;
    private final UserService userService;

    @PostMapping(path = "/register-tournament")
    public ResponseEntity<Map<String, String>> registerTournament(
            @Valid @RequestBody TournamentRegisterRequest request,
            @RequestAttribute UUID userId
    ) {
        User existingUser = userService.getUserById(userId);

        if(!userId.equals(request.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "You cannot register on behalf of another user."));
        }

        Map<String, String> response = registrationService.registerUserForTournament(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
