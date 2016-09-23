package com.zbra.go.controller;

import com.zbra.go.service.GameAlreadyStartedException;
import com.zbra.go.service.GameNotStartedException;

public interface GameController {

    void startGame() throws GameAlreadyStartedException;
    void stopGame() throws GameNotStartedException;

}
