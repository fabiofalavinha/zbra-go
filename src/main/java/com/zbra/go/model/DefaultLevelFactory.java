package com.zbra.go.model;

import com.zbra.go.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DefaultLevelFactory implements LevelFactory {

    @Autowired
    private CategoryService categoryService;

    @Override
    public Set<Level> createLevels(GameSession gameSession) {
        Set<Level> levels = new HashSet<>();
        levels.add(createLevelOne(gameSession));

        // TODO create level2, level3, level4 and level5

        return levels;
    }

    private Level createLevelOne(GameSession gameSession) {
        Level level = new Level();
        level.setName("Level 1");
        level.setGameSession(gameSession);
        Set<Place> places = new HashSet<>();

        Category restaurants = categoryService.findByType(CategoryType.RESTAURANTS);

        Place nellos = new Place();
        nellos.setName("Nellos");
        nellos.setCategory(restaurants);
        nellos.setLevel(level);
        places.add(nellos);

        Place mariaGourmet = new Place();
        mariaGourmet.setName("Maria Gourmet");
        mariaGourmet.setCategory(restaurants);
        mariaGourmet.setLevel(level);
        places.add(mariaGourmet);

        Place chines = new Place();
        chines.setName("Chinês");
        chines.setCategory(restaurants);
        chines.setLevel(level);
        places.add(chines);

        Place escadinha = new Place();
        escadinha.setName("Escadinha");
        escadinha.setCategory(restaurants);
        escadinha.setLevel(level);
        places.add(escadinha);

        Place subway = new Place();
        subway.setName("Subway");
        subway.setCategory(restaurants);
        subway.setLevel(level);
        places.add(subway);

        Place fettucine = new Place();
        fettucine.setName("Fettucine");
        fettucine.setCategory(restaurants);
        fettucine.setLevel(level);
        places.add(fettucine);

        level.setPlaces(places);
        return level;
    }
}
