package com.zbra.go;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zbra.go.controller.dto.PlayerDTO;
import com.zbra.go.controller.dto.TeamDTO;
import com.zbra.go.controller.dto.TeamEnrollmentRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamControllerTestCase {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Test
    public void testRegisterOneTeam() throws Exception {
        List<PlayerDTO> players = new ArrayList<>();
        PlayerDTO player1 = new PlayerDTO();
        player1.setKey(UUID.randomUUID().toString());
        player1.setName("Player " + new Random().nextInt());
        players.add(player1);

        List<TeamDTO> teams = new ArrayList<>();

        TeamDTO team = new TeamDTO();
        team.setKey(UUID.randomUUID().toString());
        team.setName("Team " + new Random().nextInt());
        team.setPlayers(players);
        teams.add(team);

        TeamEnrollmentRequest request = new TeamEnrollmentRequest();
        request.setTeams(teams);

        ObjectMapper o = new ObjectMapper();
        String json = o.writeValueAsString(request);

        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/teams").contentType("application/json").content(json)).andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void testRegisterTeamWithKeyNull() throws Exception {
        List<PlayerDTO> players = new ArrayList<>();
        PlayerDTO player1 = new PlayerDTO();
        // Setting team.key to NULL
        player1.setKey(null);
        player1.setName("Player " + new Random().nextInt());
        players.add(player1);

        List<TeamDTO> teams = new ArrayList<>();

        TeamDTO team = new TeamDTO();
        team.setKey(UUID.randomUUID().toString());
        team.setName("Team " + new Random().nextInt());
        team.setPlayers(players);
        teams.add(team);

        TeamEnrollmentRequest request = new TeamEnrollmentRequest();
        request.setTeams(teams);

        ObjectMapper o = new ObjectMapper();
        String json = o.writeValueAsString(request);

        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/teams").contentType("application/json").content(json)).andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}
