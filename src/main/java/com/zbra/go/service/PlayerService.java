package com.zbra.go.service;

import com.zbra.go.model.Player;

import java.util.Optional;

public interface PlayerService {

    Optional<Player> findByKey(String playerKey);

}
