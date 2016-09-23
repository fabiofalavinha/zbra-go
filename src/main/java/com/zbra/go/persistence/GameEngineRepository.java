package com.zbra.go.persistence;

import com.zbra.go.model.GameEngine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameEngineRepository extends JpaRepository<GameEngine, String> {

    Optional<GameEngine> findTopByOrderByStartedAsc();

}
