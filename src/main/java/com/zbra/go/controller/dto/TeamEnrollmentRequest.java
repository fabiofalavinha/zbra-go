package com.zbra.go.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class TeamEnrollmentRequest {

    @NotNull
    @Size(min = 1, message = "At least one team must be input")
    @Valid
    @JsonProperty
    private List<TeamDTO> teams;

    public List<TeamDTO> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDTO> teams) {
        this.teams = teams;
    }
}
