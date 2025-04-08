package com.rummy.auth.controller;

import com.rummy.auth.security.JwtTokenUtil;
import com.rummy.auth.service.ClientCredentialService;
import com.rummy.auth.model.ErrorResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/token")
public class TokenController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ClientCredentialService clientCredentialService;

    @PostMapping
    public ResponseEntity<?> getAccessToken(@Valid @RequestBody TokenRequest request) {
        // Validate request parameters
        if (request.getGrantType() == null || request.getClientId() == null || request.getClientSecret() == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("invalid_request", "Missing required parameters"));
        }

        // Validate grant type
        if (!"client_credentials".equalsIgnoreCase(request.getGrantType())) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("unsupported_grant_type", "Only 'client_credentials' grant type is supported"));
        }

        // Validate client credentials
        if (!clientCredentialService.validateClientCredentials(request.getClientId(), request.getClientSecret())) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse("invalid_client", "Invalid client credentials"));
        }

        try {
            String token = jwtTokenUtil.generateToken(request.getClientId(), request.getClientSecret());
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (Exception e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("server_error", "Error generating token"));
        }
    }
}

class TokenRequest {
    private String grantType;
    private String clientId;
    private String clientSecret;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

class TokenResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private int expiresIn = 3600; // 1 hour by default
    private String error;

    public TokenResponse(String token) {
        if (token.contains("Invalid")) {
            this.error = token;
        } else {
            this.accessToken = token;
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}