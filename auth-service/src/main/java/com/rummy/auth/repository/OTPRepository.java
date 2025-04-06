package com.rummy.auth.repository;

import com.rummy.auth.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByMobileNumberAndUsedFalse(String mobileNumber);
    Optional<OTP> findByMobileNumberAndOtpValueAndUsedFalse(String mobileNumber, String otpValue);
}