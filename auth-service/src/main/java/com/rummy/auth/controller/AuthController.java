package com.rummy.auth.controller;

import com.rummy.auth.service.OTPService;
import com.rummy.auth.service.AuthenticationService;
import com.rummy.auth.security.JwtTokenUtil;
import com.rummy.auth.model.AuthenticateRequest;
import com.rummy.auth.model.AuthenticateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
public class AuthController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/api/auth/generate-otp")
    public ResponseEntity<GenerateOTPResponse> generateOTP(
            @Valid @RequestBody GenerateOTPRequest request,
            @RequestHeader("Authorization") String authToken,
            @RequestHeader("Device-Id") String deviceId) {
        String token = authToken.replace("Bearer ", "");
        try {
            String mobileNumber = jwtTokenUtil.extractMobileNumber(token);
            String deviceIdFromToken = jwtTokenUtil.extractDeviceId(token);
            
            if (!deviceId.equals(deviceIdFromToken)) {
                return ResponseEntity.status(401).body(new GenerateOTPResponse("Invalid device ID"));
            }
            
            String otp = otpService.generateOTP(request.getMobileNumber());
            return ResponseEntity.ok(new GenerateOTPResponse("OTP sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new GenerateOTPResponse("Invalid or expired token"));
        }
    }

    @PostMapping("/api/auth/verify-otp")
    public ResponseEntity<VerifyOTPResponse> verifyOTP(
            @Valid @RequestBody VerifyOTPRequest request,
            @RequestHeader("Device-Id") String deviceId) {
        boolean isValid = otpService.validateOTP(request.getMobileNumber(), request.getOtp());
        if (isValid) {
            String token = jwtTokenUtil.generateToken(request.getMobileNumber(), deviceId);
            return ResponseEntity.ok(new VerifyOTPResponse(token));
        }
        return ResponseEntity.badRequest().body(new VerifyOTPResponse("Invalid OTP"));
    }

    @PostMapping("/api/v1/login/authenticate")
    public ResponseEntity<AuthenticateResponse> authenticate(
            @Valid @RequestBody AuthenticateRequest request) {
        AuthenticateResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);
    }
}

class GenerateOTPRequest {
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}

class GenerateOTPResponse {
    private String message;

    public GenerateOTPResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class VerifyOTPRequest {
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Pattern(regexp = "^[0-9]{6}$", message = "OTP must be 6 digits")
    private String otp;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

class VerifyOTPResponse {
    private String token;
    private String error;

    public VerifyOTPResponse(String token) {
        if (token.contains("Invalid")) {
            this.error = token;
        } else {
            this.token = token;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}