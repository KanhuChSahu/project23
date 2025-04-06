package com.rummy.player.service;

import com.rummy.player.model.Player;
import com.rummy.player.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public Player createPlayer(String mobileNumber, String name, String displayName) {
        Player player = new Player();
        player.setMobileNumber(mobileNumber);
        player.setName(name);
        player.setDisplayName(displayName);
        player.setStatus(Player.PlayerStatus.CREATED);
        player.setPanVerified(false);
        player.setBankVerified(false);
        return playerRepository.save(player);
    }

    public Optional<Player> getPlayerByMobileNumber(String mobileNumber) {
        return playerRepository.findByMobileNumber(mobileNumber);
    }

    public Player updatePlayerProfile(Player player, String name, String displayName, String profilePicture) {
        player.setName(name);
        player.setDisplayName(displayName);
        player.setProfilePicture(profilePicture);
        return playerRepository.save(player);
    }

    public Player updatePlayerStatus(Player player, Player.PlayerStatus status) {
        player.setStatus(status);
        return playerRepository.save(player);
    }

    public Player updateLoginTimestamp(Player player) {
        player.setLastLoginTimestamp(LocalDateTime.now());
        return playerRepository.save(player);
    }

    public Player verifyPanCard(Player player) {
        player.setPanVerified(true);
        return playerRepository.save(player);
    }

    public Player verifyBankAccount(Player player) {
        player.setBankVerified(true);
        return playerRepository.save(player);
    }
}