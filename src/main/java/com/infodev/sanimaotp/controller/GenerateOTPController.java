package com.infodev.sanimaotp.controller;

import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.exception.AppBlobException;
import com.infodev.sanimaotp.pojo.GenerateOtpPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.OTPChannel;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class GenerateOTPController {


    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    @Autowired
    private LogActivityService logActivityService;

    @CrossOrigin
    @RequestMapping(value = "api/generateOTP", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse> generateOTP(@RequestBody GenerateOtpPojo generateOtpRequest,
                                                      final Authentication authentication) {
        AAL2Wrap vcWrap = new AAL2Wrap();
        GlobalResponse globalResponse = new GlobalResponse();
        DataLogger dataLogger = new DataLogger("generateOTP",generateOtpRequest.getCentralId(),generateOtpRequest.getApplicationId(),generateOtpRequest.getChannelId());
        try {
            OTPChannel channel= new OTPChannel();
            channel.validateChannel(generateOtpRequest.getChannelId());
            DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(generateOtpRequest.getCentralId());
            System.out.println("Printing channel id  ==  "+generateOtpRequest.getChannelId());

            System.out.println("----------------------------");

            String blobId = digipassStatus.getSerialNumber() + generateOtpRequest.getChannelId(); //gets blob primary key -> example : serial key + channel id = FDO2212259APP5_IBANK)

            DigipassDetail digipassData = digipassDetailsRepository.getDataBySerialAppKey(blobId);

            System.out.println(digipassData.getAppType());
            System.out.println(digipassData.getAppBlob());
            System.out.println(digipassData.getSerialAppKey());

            KernelParms kernelParms = new KernelParms();
            byte[] appBlob = digipassData.getAppBlob().getBytes();
            int pWord = vcWrap.AAL2GenPassword(appBlob, kernelParms, generateOtpRequest.getChallenge());
            digipassData.setAppBlob(new String(appBlob));
            digipassDetailsRepository.save(digipassData);
            if (pWord == 0) {
                digipassData.setAppBlob(new String(appBlob));
                globalResponse.setData(vcWrap.getStringResponse());
                System.out.println(vcWrap.getStringResponse());
                digipassDetailsRepository.save(digipassData);
                globalResponse.setStatus(1);
                globalResponse.setMessage("OTP generation successful.");
            } else {
                throw new AppBlobException("OTP generation failed !");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            globalResponse.setMessage(e.getMessage());
            globalResponse.setStatus(0);
            globalResponse.setData(null);
        }
        finally{
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setExtraInfo1("generatedOTP:"+globalResponse.getData());
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);
            return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
        }


    }
}