package com.gametosa.tournament.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentRegistrationDto {
    private UUID id;
    private UUID userId;
    private UUID tournamentId;
}
