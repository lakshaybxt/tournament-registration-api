package com.gametosa.tournament.service;

import com.gametosa.tournament.domain.dto.TournamentRegisterRequest;
import jakarta.validation.Valid;

import java.util.Map;

public interface TournamentRegistrationService {
    Map<String, String> registerUserForTournament(TournamentRegisterRequest request);
}
