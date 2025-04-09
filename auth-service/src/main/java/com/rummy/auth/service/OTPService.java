package com.rummy.auth.service;

import com.rummy.auth.model.OTP;
import com.rummy.auth.model.OTPStatus;
import com.rummy.auth.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OTPService {
    @Autowired
    private OTPRepository otpRepository;

    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_VERIFICATION_ATTEMPTS = 3;

    public String generateOTP(String mobileNumber, String deviceId) {
        // Invalidate any existing pending OTP
        otpRepository.findByMobileNumberAndStatus(mobileNumber, OTPStatus.PENDING)
                .ifPresent(existingOTP -> {
                    existingOTP.setStatus(OTPStatus.EXPIRED);
                    otpRepository.save(existingOTP);
                });

        // Generate new OTP
        String otpCode = generateRandomOTP();
        OTP otp = new OTP();
        otp.setMobileNumber(mobileNumber);
        otp.setOtpCode(otpCode);
        otp.setDeviceId(deviceId);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otp.setStatus(OTPStatus.PENDING);
        otp.setVerificationAttempts(0);
        otp.setVerified(false);

        otpRepository.save(otp);
        return otpCode;
    }

    public boolean validateOTP(String mobileNumber, String otpCode, String deviceId) {
        return otpRepository.findByMobileNumberAndOtpCodeAndStatus(mobileNumber, otpCode, OTPStatus.PENDING)
                .filter(otp -> {
                    if (!deviceId.equals(otp.getDeviceId())) {
                        return false;
                    }
                    if (LocalDateTime.now().isAfter(otp.getExpiresAt())) {
                        otp.setStatus(OTPStatus.EXPIRED);
                        otpRepository.save(otp);
                        return false;
                    }
                    if (otp.getVerificationAttempts() >= MAX_VERIFICATION_ATTEMPTS) {
                        otp.setStatus(OTPStatus.EXPIRED);
                        otpRepository.save(otp);
                        return false;
                    }
                    return true;
                })
                .map(otp -> {
                    otp.setVerificationAttempts(otp.getVerificationAttempts() + 1);
                    if (otpCode.equals(otp.getOtpCode())) {
                        otp.setStatus(OTPStatus.VERIFIED);
                        otp.setVerified(true);
                        otpRepository.save(otp);
                        return true;
                    }
                    otpRepository.save(otp);
                    return false;
                })
                .orElse(false);
    }

    private String generateRandomOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}