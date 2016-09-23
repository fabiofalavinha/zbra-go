package com.zbra.go.controller;

import com.zbra.go.service.GameAlreadyStartedException;
import com.zbra.go.service.GameEngineService;
import com.zbra.go.service.GameNotStartedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultGameController implements GameController {

    @Autowired
    private GameEngineService gameEngineService;

    @Override
    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public void startGame() throws GameAlreadyStartedException {
        gameEngineService.start();
    }

    @Override
    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public void stopGame() throws GameNotStartedException {
        gameEngineService.stop();
    }

}
