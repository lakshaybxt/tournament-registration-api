package com.gametosa.tournament.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentRequestDto {

    @NotBlank(message = "Tournament name cannot be null")
    private String name;
}
