package com.gametosa.tournament.service.impl;

import com.gametosa.tournament.domain.dto.TournamentRequestDto;
import com.gametosa.tournament.domain.entity.Tournament;
import com.gametosa.tournament.repository.TournamentRepository;
import com.gametosa.tournament.service.TournamentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Override
    public Tournament createTournament(TournamentRequestDto request) {
        Tournament tournament = Tournament.builder()
                .name(request.getName())
                .build();

        return tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> findALlTournament() {
        return tournamentRepository.findAll();
    }

    @Override
    public Tournament getTournamentById(UUID id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with the Id"));
    }
}
