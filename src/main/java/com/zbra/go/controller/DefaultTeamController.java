package com.zbra.go.controller;

import com.zbra.go.controller.dto.TeamConverter;
import com.zbra.go.controller.dto.TeamDTO;
import com.zbra.go.controller.dto.TeamEnrollmentRequest;
import com.zbra.go.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class DefaultTeamController implements TeamController {

    @Autowired
    private TeamService teamService;

    @Override
    @RequestMapping(path = "/teams", method = RequestMethod.POST)
    public void registerTeams(@Valid @RequestBody TeamEnrollmentRequest request) {
        request.getTeams().stream().map(TeamConverter::fromTeam).collect(Collectors.toList()).forEach(t -> teamService.save(t));
    }

    @Override
    @RequestMapping(path = "/teams", method = RequestMethod.GET)
    public List<TeamDTO> getTeams() {
        return teamService.findAll().stream().map(TeamConverter::toTeams).collect(Collectors.toList());
    }
}
