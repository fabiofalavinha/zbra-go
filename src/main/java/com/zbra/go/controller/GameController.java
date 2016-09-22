package com.zbra.go.controller;

import com.zbra.go.service.GameEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private GameEngineService gameEngineService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public void startGame() {
        gameEngineService.start();
    }

    @RequestMapping(value = "/stop", method = RequestMethod.POST)
    public void stopGame() {
        gameEngineService.stop();
    }

}
