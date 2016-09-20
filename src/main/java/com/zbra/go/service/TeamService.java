package com.zbra.go.service;

import com.zbra.go.model.Team;

import java.util.List;

public interface TeamService {

    List<Team> findAll();
    void save(Team team);

}
