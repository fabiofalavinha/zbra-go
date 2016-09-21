package com.zbra.go.persistence;

import com.zbra.go.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    Player findByKey(String key);

}
