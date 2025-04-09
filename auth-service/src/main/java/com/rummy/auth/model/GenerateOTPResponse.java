package com.rummy.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOTPResponse {
    private boolean error;
    private int code;
    private String message;

    public GenerateOTPResponse(String message) {
        this.message = message;
    }

    public static GenerateOTPResponse success() {
        return new GenerateOTPResponse(false, 1000, "OTP sent successfully");
    }

    public static GenerateOTPResponse invalidToken() {
        return new GenerateOTPResponse(true, 4001, "Invalid or expired access token");
    }

    public static GenerateOTPResponse invalidDevice() {
        return new GenerateOTPResponse(true, 4002, "Invalid device ID");
    }
}