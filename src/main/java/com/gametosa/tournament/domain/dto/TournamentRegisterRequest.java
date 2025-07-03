package com.gametosa.tournament.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentRegisterRequest {

    @NotNull(message = "User Id is required")
    private UUID userId;

    @NotNull(message = "Tournament Id is required")
    private UUID tournamentId;
}
