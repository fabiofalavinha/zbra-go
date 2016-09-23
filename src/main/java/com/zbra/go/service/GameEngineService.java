package com.zbra.go.service;

import com.zbra.go.model.GameSession;
import com.zbra.go.model.Team;

import java.util.Optional;

public interface GameEngineService {

    boolean hasStarted();
    Optional<GameSession> findGameSessionByTeam(Team team);
    void start() throws GameAlreadyStartedException;
    void stop() throws GameNotStartedException;

}
