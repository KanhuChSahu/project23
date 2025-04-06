package com.rummy.player.dto.response;

import com.rummy.player.model.Player;

public class PlayerResponse {
    private Long playerId;
    private String mobileNumber;
    private String name;
    private String displayName;
    private String profilePicture;
    private boolean panVerified;
    private boolean bankVerified;
    private Player.PlayerStatus status;

    public PlayerResponse(Player player) {
        this.playerId = player.getPlayerId();
        this.mobileNumber = player.getMobileNumber();
        this.name = player.getName();
        this.displayName = player.getDisplayName();
        this.profilePicture = player.getProfilePicture();
        this.panVerified = player.isPanVerified();
        this.bankVerified = player.isBankVerified();
        this.status = player.getStatus();
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isPanVerified() {
        return panVerified;
    }

    public void setPanVerified(boolean panVerified) {
        this.panVerified = panVerified;
    }

    public boolean isBankVerified() {
        return bankVerified;
    }

    public void setBankVerified(boolean bankVerified) {
        this.bankVerified = bankVerified;
    }

    public Player.PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(Player.PlayerStatus status) {
        this.status = status;
    }
}