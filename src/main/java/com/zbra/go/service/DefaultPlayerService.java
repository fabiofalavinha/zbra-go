package com.zbra.go.service;

import com.zbra.go.model.Player;
import com.zbra.go.persistence.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class DefaultPlayerService implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Player> findByKey(String key) {
        return Optional.ofNullable(playerRepository.findByKey(key));
    }
}
