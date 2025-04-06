package com.rummy.auth.service;

import com.rummy.auth.model.OTP;
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

    public String generateOTP(String mobileNumber) {
        // Invalidate any existing unused OTP
        otpRepository.findByMobileNumberAndUsedFalse(mobileNumber)
                .ifPresent(existingOTP -> {
                    existingOTP.setUsed(true);
                    otpRepository.save(existingOTP);
                });

        // Generate new OTP
        String otpValue = generateRandomOTP();
        OTP otp = new OTP();
        otp.setMobileNumber(mobileNumber);
        otp.setOtpValue(otpValue);
        otp.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        otp.setUsed(false);

        otpRepository.save(otp);
        return otpValue;
    }

    public boolean validateOTP(String mobileNumber, String otpValue) {
        return otpRepository.findByMobileNumberAndOtpValueAndUsedFalse(mobileNumber, otpValue)
                .filter(otp -> LocalDateTime.now().isBefore(otp.getExpiryTime()))
                .map(otp -> {
                    otp.setUsed(true);
                    otpRepository.save(otp);
                    return true;
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