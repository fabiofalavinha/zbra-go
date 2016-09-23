package com.zbra.go.service;

public class GameNotStartedException extends Exception {

    public GameNotStartedException() {
        super("Game not started yet");
    }

}
