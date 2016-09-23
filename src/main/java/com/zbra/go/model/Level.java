package com.zbra.go.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
public class Level {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true, mappedBy = "level")
    private Set<Place> places;

    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private GameSession gameSession;

    @Column(nullable = false)
    private Date started;

    @Column
    private Date ended;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private LevelType levelType;

    public Level() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Place> getPlaces() {
        return places;
    }

    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }
}
