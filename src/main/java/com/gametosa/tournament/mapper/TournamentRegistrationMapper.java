package com.gametosa.tournament.mapper;

import com.gametosa.tournament.domain.dto.TournamentRegistrationDto;
import com.gametosa.tournament.domain.entity.TournamentRegistration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TournamentRegistrationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "tournamentId", source = "tournament.id")
    TournamentRegistrationDto toDto(TournamentRegistration tournamentRegistration);
}
