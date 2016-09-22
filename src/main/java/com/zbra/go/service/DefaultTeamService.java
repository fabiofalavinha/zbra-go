package com.zbra.go.service;

import com.zbra.go.model.Player;
import com.zbra.go.model.Team;
import com.zbra.go.persistence.PlayerRepository;
import com.zbra.go.persistence.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
class DefaultTeamService implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    @Transactional
    @Override
    public void save(Team team) {
        final String teamKey = team.getKey();
        final Team existed = teamRepository.findByKey(teamKey);
        if (existed != null) {
            throw new IllegalStateException(String.format("Team with key [%s] already existed on repository", teamKey));
        }
        team.getPlayers().forEach(p -> {
            Player playerExisted = playerRepository.findByKey(p.getKey());
            if (playerExisted != null) {
                throw new IllegalStateException(String.format("Player with key [%s] already existed on repository", p.getKey()));
            }
        });
        teamRepository.save(team);
    }
}
