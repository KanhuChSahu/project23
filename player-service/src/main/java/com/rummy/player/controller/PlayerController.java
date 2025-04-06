package com.rummy.player.controller;

import com.rummy.player.model.Player;
import com.rummy.player.service.PlayerService;
import com.rummy.player.dto.request.RegisterPlayerRequest;
import com.rummy.player.dto.request.UpdateProfileRequest;
import com.rummy.player.dto.response.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/register")
    public ResponseEntity<PlayerResponse> registerPlayer(
            @Valid @RequestBody RegisterPlayerRequest request) {
        Player player = playerService.createPlayer(
                request.getMobileNumber(),
                request.getName(),
                request.getDisplayName());
        return ResponseEntity.ok(new PlayerResponse(player));
    }

    @GetMapping("/{mobileNumber}")
    public ResponseEntity<PlayerResponse> getPlayerProfile(
            @PathVariable String mobileNumber) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> ResponseEntity.ok(new PlayerResponse(player)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{mobileNumber}/profile")
    public ResponseEntity<PlayerResponse> updateProfile(
            @PathVariable String mobileNumber,
            @Valid @RequestBody UpdateProfileRequest request) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> {
                    Player updatedPlayer = playerService.updatePlayerProfile(
                            player,
                            request.getName(),
                            request.getDisplayName(),
                            request.getProfilePicture());
                    return ResponseEntity.ok(new PlayerResponse(updatedPlayer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{mobileNumber}/verify-pan")
    public ResponseEntity<PlayerResponse> verifyPanCard(
            @PathVariable String mobileNumber) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> {
                    Player updatedPlayer = playerService.verifyPanCard(player);
                    return ResponseEntity.ok(new PlayerResponse(updatedPlayer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{mobileNumber}/verify-bank")
    public ResponseEntity<PlayerResponse> verifyBankAccount(
            @PathVariable String mobileNumber) {
        return playerService.getPlayerByMobileNumber(mobileNumber)
                .map(player -> {
                    Player updatedPlayer = playerService.verifyBankAccount(player);
                    return ResponseEntity.ok(new PlayerResponse(updatedPlayer));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}