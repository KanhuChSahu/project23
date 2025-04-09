package com.rummy.auth.repository;

import com.rummy.auth.model.OTP;
import com.rummy.auth.model.OTPStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByMobileNumberAndStatus(String mobileNumber, OTPStatus status);
    Optional<OTP> findByMobileNumberAndOtpCodeAndStatus(String mobileNumber, String otpCode, OTPStatus status);
}