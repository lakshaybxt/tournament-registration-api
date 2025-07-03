package com.gametosa.tournament.repository;

import com.gametosa.tournament.domain.entity.Tournament;
import com.gametosa.tournament.domain.entity.TournamentRegistration;
import com.gametosa.tournament.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TournamentRegistrationRepository extends JpaRepository<TournamentRegistration, UUID> {
    Optional<TournamentRegistration> findByUserAndTournament(User user, Tournament tournament);
}
