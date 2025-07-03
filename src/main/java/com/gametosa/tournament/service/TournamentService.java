package com.gametosa.tournament.service;

import com.gametosa.tournament.domain.dto.TournamentRequestDto;
import com.gametosa.tournament.domain.entity.Tournament;

import java.util.List;
import java.util.UUID;

public interface TournamentService {
    Tournament createTournament(TournamentRequestDto request);
    List<Tournament> findALlTournament();
    Tournament getTournamentById(UUID id);
}
