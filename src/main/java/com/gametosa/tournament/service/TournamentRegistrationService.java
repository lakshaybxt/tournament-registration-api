package com.gametosa.tournament.service;

import com.gametosa.tournament.domain.dto.TournamentRegisterRequest;
import com.gametosa.tournament.domain.entity.TournamentRegistration;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

public interface TournamentRegistrationService {
    Map<String, String> registerUserForTournament(TournamentRegisterRequest request);
    List<TournamentRegistration> getAllRegistration();
}
