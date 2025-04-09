package com.rummy.auth.service;

import com.rummy.auth.model.AuthenticateRequest;
import com.rummy.auth.model.AuthenticateResponse;
import com.rummy.auth.model.AuthenticateResponse.AuthenticateData;
import com.rummy.auth.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public AuthenticateResponse authenticate(AuthenticateRequest request, String accessToken, String deviceId) {
        // Validate access token
        if (!jwtTokenUtil.validateToken(accessToken)) {
            return new AuthenticateResponse(true, 4001, "Invalid or expired access token.", null);
        }

        // Validate device ID from token
        String deviceIdFromToken = jwtTokenUtil.extractDeviceId(accessToken);
        if (!deviceId.equals(deviceIdFromToken)) {
            return new AuthenticateResponse(true, 4002, "Invalid device ID.", null);
        }

        // Validate OTP
        boolean isValidOtp = otpService.validateOTP(request.getMobileNo(), request.getOtp(), deviceId);
        if (!isValidOtp) {
            return new AuthenticateResponse(true, 2004, "Invalid or expired OTP.", null);
        }

        try {
            // Generate session ID and auth token
            String sessionId = UUID.randomUUID().toString();
            String authToken = jwtTokenUtil.generateToken(request.getMobileNo(), sessionId);

            // Create response data
            AuthenticateData data = new AuthenticateData(sessionId, authToken);
            return new AuthenticateResponse(false, 1000, "Authenticated successfully.", data);

        } catch (Exception e) {
            return new AuthenticateResponse(true, 5000, "Internal server error.", null);
        }
    }
}