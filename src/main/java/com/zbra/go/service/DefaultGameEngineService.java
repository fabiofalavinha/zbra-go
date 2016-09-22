package com.zbra.go.service;

import com.zbra.go.model.GameSession;
import com.zbra.go.model.Level;
import com.zbra.go.model.LevelFactory;
import com.zbra.go.persistence.GameSessionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
class DefaultGameEngineService implements GameEngineService {

    @Autowired
    private TeamService teamService;

    @Autowired
    private LevelFactory levelFactory;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Transactional
    @Override
    public void start() {
        final List<GameSession> gameSessions = gameSessionRepository.findAll();

        if (!gameSessions.stream().allMatch(GameSession::hasEnded)) {
            throw new IllegalStateException("Can't started again because we still have teams playing");
        }

        final DateTime gameStarted = DateTime.now();

        teamService.findAll().forEach(t -> {
            GameSession gameSession = new GameSession();
            gameSession.setTeam(t);
            Set<Level> levels = levelFactory.createLevels(gameSession);
            gameSession.setLevels(levels);
            gameSession.setCurrentLevel(levels.stream().findFirst().get());
            gameSession.setStarted(gameStarted.toDate());

            gameSessionRepository.save(gameSession);
        });
    }

    @Transactional
    @Override
    public void stop() {
        final DateTime end = DateTime.now();

        gameSessionRepository.findAll().forEach(s -> {
            s.setEnded(end.toDate());

            gameSessionRepository.save(s);
        });
    }
}
