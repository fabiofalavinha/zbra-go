package com.zbra.go.controller.dto;

import com.zbra.go.model.Player;
import com.zbra.go.model.Team;

import java.util.List;
import java.util.stream.Collectors;

public final class TeamConverter {

    public static List<Team> fromTeams(List<TeamDTO> list) {
        return list.stream().map(TeamConverter::fromTeam).collect(Collectors.toList());
    }

    public static Team fromTeam(TeamDTO teamDTO) {
        Team team = new Team();
        team.setKey(teamDTO.getKey());
        team.setName(teamDTO.getName());
        team.setPlayers(teamDTO.getPlayers().stream().map(p -> {
            Player player = new Player();
            player.setKey(p.getKey());
            player.setName(p.getName());
            player.setTeam(team);
            return player;
        }).collect(Collectors.toSet()));
        return team;
    }

    public static TeamDTO toTeams(Team team) {
        TeamDTO dto = new TeamDTO();
        dto.setKey(team.getKey());
        dto.setName(team.getName());
        dto.setPlayers(team.getPlayers().stream().map(p -> {
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setKey(p.getKey());
            playerDTO.setName(p.getName());
            return playerDTO;
        }).collect(Collectors.toList()));
        return dto;
    }

    private TeamConverter() {
    }
}
