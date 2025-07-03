package com.gametosa.tournament.service.impl;

import com.gametosa.tournament.domain.dto.TournamentRegisterRequest;
import com.gametosa.tournament.domain.entity.Tournament;
import com.gametosa.tournament.domain.entity.TournamentRegistration;
import com.gametosa.tournament.domain.entity.User;
import com.gametosa.tournament.repository.TournamentRegistrationRepository;
import com.gametosa.tournament.repository.TournamentRepository;
import com.gametosa.tournament.repository.UserRepository;
import com.gametosa.tournament.service.TournamentRegistrationService;
import com.gametosa.tournament.service.TournamentService;
import com.gametosa.tournament.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TournamentRegistrationServiceImpl implements TournamentRegistrationService {

    private final TournamentRegistrationRepository registrationRepository;
    private final UserService userService;
    private final TournamentService tournamentService;

    @Override
    public Map<String, String> registerUserForTournament(TournamentRegisterRequest request) {

        User user = userService.getUserById(request.getUserId());
        Tournament tournament = tournamentService.getTournamentById(request.getTournamentId());

        boolean alreadyRegistered = registrationRepository
                .findByUserAndTournament(user, tournament)
                .isPresent();

        if(alreadyRegistered) {
            return Map.of("message", "User already registered." );
        }

        TournamentRegistration registration = TournamentRegistration.builder()
                .user(user)
                .tournament(tournament)
                .build();

        registrationRepository.save(registration);
        return Map.of("message", "Registration successful");
    }
}
