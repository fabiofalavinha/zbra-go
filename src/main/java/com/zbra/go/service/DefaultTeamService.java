package com.zbra.go.service;

import com.zbra.go.model.Team;
import com.zbra.go.persistence.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class DefaultTeamService implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Team team) {
        teamRepository.save(team);
    }
}
