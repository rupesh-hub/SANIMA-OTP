package com.infodev.sanimaotp.controller.ApiController;


import com.google.gson.Gson;
import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.OtpValidationPojo;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import com.infodev.sanimaotp.services.apiService.validateOTP.ValidateOTPService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@Api(description="Determines if submitted OTP is valid or not. ")
@Slf4j
public class ValidateOTPController {

    @Autowired
    private ValidateOTPService validateOTPService;

    @Autowired
    private LogActivityService logActivityService;


    @RequestMapping(value = "api/validateOTP", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<GlobalResponse> validateOTP(@Valid @RequestBody OtpValidationPojo otpValidationPojo,
                                                      BindingResult bindingResult,
                                                      final Authentication authentication) {
        Gson gson = new Gson();
        log.info("Request body is {}",gson.toJson(otpValidationPojo));
        GlobalResponse response= new GlobalResponse();
        DataLogger dataLogger = new DataLogger("validateOTP",otpValidationPojo.getCentralId(),otpValidationPojo.getApplicationId(),otpValidationPojo.getChannelId());


        try{
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
            response=validateOTPService.validateOtp(otpValidationPojo);

        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(0);
            response.setData(null);
            response.setMessage(e.getMessage());

        }finally {

            dataLogger.setActivityStatus(response.getStatus());
            dataLogger.setActivityDescription(response.getMessage());
            dataLogger.setExtraInfo1("otp:"+otpValidationPojo.getOtp()+"/"+"challenge:"+otpValidationPojo.getChallenge());
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);

            return new ResponseEntity<GlobalResponse>(response,HttpStatus.OK);
        }
    }
}
