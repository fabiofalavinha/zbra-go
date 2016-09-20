package com.zbra.go.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TeamDTO {

    @NotNull(message = "Team key is required")
    @JsonProperty
    private String key;

    @NotNull(message = "Team name is required")
    @JsonProperty
    private String name;

    @NotNull(message = "Players are required")
    @Valid
    @JsonProperty
    private List<PlayerDTO> players;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
