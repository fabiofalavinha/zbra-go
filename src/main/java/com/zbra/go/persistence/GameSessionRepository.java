package com.zbra.go.persistence;

import com.zbra.go.model.GameSession;
import com.zbra.go.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameSessionRepository extends JpaRepository<GameSession, String> {

    Optional<GameSession> findByTeam(Team team);

}
