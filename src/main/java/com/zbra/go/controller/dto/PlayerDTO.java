package com.zbra.go.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PlayerDTO {

    @NotNull(message = "Player key is required")
    @JsonProperty
    private String key;

    @NotNull(message = "Player name is required")
    @JsonProperty
    private String name;

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
}
