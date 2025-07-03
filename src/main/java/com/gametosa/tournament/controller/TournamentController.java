package com.gametosa.tournament.controller;

import com.gametosa.tournament.domain.dto.TournamentRequestDto;
import com.gametosa.tournament.domain.entity.Tournament;
import com.gametosa.tournament.response.TournamentResponse;
import com.gametosa.tournament.service.TournamentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/tournament")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping
    public ResponseEntity<List<TournamentResponse>> getAllTournament() {
        List<Tournament> tournaments = tournamentService.findALlTournament();
        List<TournamentResponse> responses = tournaments.stream()
                .map(tournament -> TournamentResponse.builder()
                        .id(tournament.getId())
                        .name(tournament.getName())
                        .build()
                ).toList();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<TournamentResponse> createTournament(@Valid @RequestBody TournamentRequestDto request) {
        Tournament tournament = tournamentService.createTournament(request);
        TournamentResponse response = TournamentResponse.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TournamentResponse> getTournament(@PathVariable UUID id) {
        Tournament tournament = tournamentService.getTournamentById(id);
        TournamentResponse response = TournamentResponse.builder()
                .id(tournament.getId())
                .name(tournament.getName())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
