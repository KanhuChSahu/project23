package com.rummy.player.repository;

import com.rummy.player.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
    Optional<Player> findByMobileNumberAndStatus(String mobileNumber, Player.PlayerStatus status);
}