package com.zbra.go.model;

import java.util.Set;

public interface LevelFactory {

    Set<Level> createLevels(GameSession gameSession);

}
