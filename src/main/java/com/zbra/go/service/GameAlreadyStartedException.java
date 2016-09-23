package com.zbra.go.service;

public class GameAlreadyStartedException extends Exception {

    public GameAlreadyStartedException() {
        super("Game already started");
    }

}
