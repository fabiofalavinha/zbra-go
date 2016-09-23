package com.zbra.go.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
public class GameSession {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Team team;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "gameSession")
    private Set<Level> levels;

    @Column
    @Enumerated(value = EnumType.STRING)
    private LevelType currentLevelType;

    public GameSession() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Level> getLevels() {
        return levels;
    }

    public void setLevels(Set<Level> levels) {
        this.levels = levels;
    }

    public LevelType getCurrentLevelType() {
        return currentLevelType;
    }

    public void setCurrentLevelType(LevelType currentLevelType) {
        this.currentLevelType = currentLevelType;
    }

    public Level getCurrentLevelByType() {
        return levels.stream().filter(l -> l.getLevelType().equals(currentLevelType)).findAny().get();
    }
}
