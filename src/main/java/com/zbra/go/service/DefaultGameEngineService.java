package com.zbra.go.service;

import com.zbra.go.model.*;
import com.zbra.go.persistence.GameEngineRepository;
import com.zbra.go.persistence.GameSessionRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
class DefaultGameEngineService implements GameEngineService {

    @Autowired
    private GameEngineRepository gameEngineRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private LevelFactory levelFactory;

    @Autowired
    private GameSessionRepository gameSessionRepository;

    @Transactional
    @Override
    public void start() throws GameAlreadyStartedException {
        GameEngine gameEngine;

        Optional<GameEngine> gameEngineMaybe = gameEngineRepository.findTopByOrderByStartedAsc();
        if (!gameEngineMaybe.isPresent()) {
            gameEngine = new GameEngine();
        } else {
            gameEngine = gameEngineMaybe.get();

            if (!gameEngine.hasEnded()) {
                throw new GameAlreadyStartedException();
            }

            gameEngine.setEnded(null);
        }
        gameEngine.setStarted(DateTime.now().toDate());
        gameEngineRepository.save(gameEngine);

        final List<GameSession> gameSessions = gameSessionRepository.findAll();

        final Date gameStarted = gameEngine.getStarted();

        teamService.findAll().forEach(t -> {
            GameSession gameSession;
            Optional<GameSession> gameSessionMaybe = gameSessions.stream().filter(s -> s.getTeam().equals(t)).findAny();
            if (!gameSessionMaybe.isPresent()) {
                gameSession = new GameSession();
                gameSession.setTeam(t);

                Set<Level> levels = levelFactory.createLevels(gameSession);
                gameSession.setLevels(levels);

                Level firstLevel = levels.iterator().next();
                firstLevel.setStarted(gameStarted);
                gameSession.setCurrentLevel(firstLevel);
            } else {
                gameSession = gameSessionMaybe.get();
                Level currentLevel = gameSession.getCurrentLevel();
                currentLevel.setEnded(null);
                currentLevel.setStarted(gameStarted);
            }
            gameSessionRepository.save(gameSession);
        });
    }

    @Transactional
    @Override
    public void stop() throws GameNotStartedException {
        Optional<GameEngine> gameEngineMaybe = gameEngineRepository.findTopByOrderByStartedAsc();
        if (!gameEngineMaybe.isPresent()) {
            throw new GameNotStartedException();
        }

        final DateTime end = DateTime.now();

        GameEngine gameEngine = gameEngineMaybe.get();
        gameEngine.setEnded(end.toDate());

        gameEngineRepository.save(gameEngine);

        gameSessionRepository.findAll().forEach(s -> {
            s.getCurrentLevel().setEnded(end.toDate());
            gameSessionRepository.save(s);
        });
    }

    @Transactional(readOnly = true)
    @Override
    public boolean hasStarted() {
        Optional<GameEngine> gameEngineMaybe = gameEngineRepository.findTopByOrderByStartedAsc();
        return gameEngineMaybe.isPresent() && !gameEngineMaybe.get().hasEnded();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<GameSession> findGameSessionByTeam(Team team) {
        return gameSessionRepository.findByTeam(team);
    }
}
