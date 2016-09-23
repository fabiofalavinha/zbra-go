package com.zbra.go.controller;

import com.zbra.go.controller.dto.TeamDTO;
import com.zbra.go.controller.dto.TeamEnrollmentRequest;

import java.util.List;

public interface TeamController {

    List<TeamDTO> getTeams();
    void registerTeams(TeamEnrollmentRequest request);

}
