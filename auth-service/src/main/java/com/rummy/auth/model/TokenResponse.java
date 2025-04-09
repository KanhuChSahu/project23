package com.rummy.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private int expiresIn = 3600; // Token validity in seconds

    public TokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}