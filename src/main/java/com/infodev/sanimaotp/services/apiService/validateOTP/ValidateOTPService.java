package com.infodev.sanimaotp.services.apiService.validateOTP;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.OtpValidationPojo;

public interface ValidateOTPService{
    public GlobalResponse validateOtp(OtpValidationPojo otpValidationPojo) throws Exception;
}
