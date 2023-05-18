package com.infodev.sanimaotp.services.apiService.validateOTP;

import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.OtpValidationPojo;
import com.infodev.sanimaotp.services.utils.OTPChannel;
import com.infodev.sanimaotp.services.utils.SetGetAppIdInStatus;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ValidateOTPServiceImpl implements ValidateOTPService {

//    public final String ANSI_RESET = "\u001B[0m";
//    public final String ANSI_BLACK = "\u001B[30m";
//    public final String ANSI_RED = "\u001B[31m";
//    public final String ANSI_GREEN = "\u001B[32m";
//    public final String ANSI_YELLOW = "\u001B[33m";
//    public final String ANSI_BLUE = "\u001B[34m";
//    public final String ANSI_PURPLE = "\u001B[35m";
//    public final String ANSI_CYAN = "\u001B[36m";
//    public final String ANSI_WHITE = "\u001B[37m";


    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public GlobalResponse validateOtp(OtpValidationPojo otpPojo) throws Exception {
        GlobalResponse globalResponse = new GlobalResponse();
        AAL2Wrap aal2Wrap = new AAL2Wrap();
        try {
            logger.info("Validating OTP Channel");
            OTPChannel otpChannel = new OTPChannel();
            otpChannel.validateChannel(otpPojo.getChannelId());

            DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(otpPojo.getCentralId());

            if (digipassStatus == null) {
                throw new Exception("User not enrolled");
            }
            if (digipassStatus.getStatus().equals("A")) {
                SetGetAppIdInStatus setGetAppIdInStatus = new SetGetAppIdInStatus(otpPojo.getChannelId(),otpPojo.getApplicationId());
                String appIdInDatabase=setGetAppIdInStatus.getAppId(digipassStatus);
                if((otpPojo.getApplicationId().equals(appIdInDatabase)) || appIdInDatabase == null){
                    String blobId = digipassStatus.getSerialNumber() + otpPojo.getChannelId();  //gets blob primary key -> serial key + channel id = FDO2212259APP1_MB)

                    DigipassDetail digipassDetail = digipassDetailsRepository.getDataBySerialAppKey(blobId); //fetch blob row based on blob id

                    //System.out.println();
                    //System.out.println(ANSI_GREEN+"Before calling AAL2VerifyPassword"+ANSI_RESET);
                    //System.out.println();

                    //System.out.println(ANSI_BLUE+"Request parameters :");
                    //System.out.println("Channel :" + otpPojo.getChannelId());
                    //System.out.println("Challenge :" + otpPojo.getChallenge());
                    //System.out.println("Password :" + otpPojo.getOtp());
                    //System.out.println("Application ID :" + otpPojo.getApplicationId());
                    //System.out.println("Central Id: " + otpPojo.getCentralId());

                    //System.out.println("Blob Id: "+digipassDetail.getSerialAppKey());
                    //System.out.println("BLOB : "+digipassDetail.getAppBlob()+ANSI_RESET);


                    byte[] appBlob = digipassDetail.getAppBlob().getBytes();

                    KernelParms kernelParms = new KernelParms();

                    kernelParms.setParm(4, 3);//9851210613   9803072030 bikesh  9851097093


//                String appTypeBefore = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 1);
//                String useCountBefore = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 2);
//                String lastTimeUsageCountBefore = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 3);
//                String timeShiftBefore = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 4);

                    //logger.info("AppType :" + appTypeBefore + " Use count : " + useCountBefore + " Last time usage :" + lastTimeUsageCountBefore);
                    //System.out.println(ANSI_PURPLE+"AppType :" + appTypeBefore + " Use count : "+ANSI_RED + useCountBefore +ANSI_PURPLE+ " Last time usage :" +ANSI_RED+ lastTimeUsageCountBefore+ANSI_RESET);
                    //System.out.println("Time Shift :"+timeShiftBefore);


//                int[] kparams = kernelParms.getParms();
//                String[] kparamName = {
//                        "ITimeWindow",
//                        "STimeWindow",
//                        "DiagLevel",
//                        "GMTAdjust",
//                        "CheckChallenge",
//                        "IThreshold",
//                        "SThreshold",
//                        "ChkInactDays",
//                        "DeriveVector",
//                        "SyncWindow",
//                        "OnLineSG",
//                        "EventWindow",
//                        "HSMSlotID",
//                        "StorageKeyID",
//                        "TransportKeyID",
//                        "StorageDeriveKey1",
//                        "StorageDeriveKey2",
//                        "StorageDeriveKey3",
//                        "StorageDeriveKey4"
//                };
//                for (int i = 0; i < kparams.length; i++) { //63772028  //transportKey : 8388607
//                    //System.out.println("Index " + i + " : " + kparamName[i] + " : " + kparams[i]);
//                }

                    logger.info("Validating Entered OTP {}", otpPojo.getOtp());
                    int validationStatus = aal2Wrap.AAL2VerifyPassword(appBlob, kernelParms, otpPojo.getOtp(), otpPojo.getChallenge());

                    //now update blob as verify password api is called

                    digipassDetail.setAppBlob(new String(appBlob)); //set updated blob to object


                    digipassDetailsRepository.save(digipassDetail); //save the blob back to database

//                String appTypeAfter = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 1);
//                String useCountAfter = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 2);
//                String lastTimeUsageCountAfter = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 3);
//                String timeShift = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kernelParms, 4);
                    //System.out.println();
                    //System.out.println(ANSI_GREEN+"After calling AAL2VerifyPassword"+ANSI_RESET);
                    //System.out.println();

                    //System.out.println(ANSI_BLUE+"Blob Id: "+digipassDetail.getSerialAppKey());
                    //System.out.println("BLOB : " +digipassDetail.getAppBlob()+ANSI_RESET);


                    //System.out.println(ANSI_PURPLE+"AppType :"+ ANSI_RED + appTypeAfter +ANSI_PURPLE+ " Use count : "+ANSI_RED + useCountAfter +ANSI_RESET+ " Last time usage :" +ANSI_RED+ lastTimeUsageCountAfter+ANSI_RESET);
                    //System.out.println("Time shift :"+ timeShift);

                    //this.getBlobDetail(appBlob, kernelParms);

                    //boolean validationSuccessInterval = (validationStatus > 10001 && validationStatus < 10007) ? true : false;

                    if (validationStatus == 0) {

                        globalResponse.setStatus(1);
                        globalResponse.setMessage("OTP validation successful !");
                        globalResponse.setData(validationStatus);

                        //System.out.println(ANSI_GREEN+"OTP validation successful !"+ANSI_RESET);

                        //auto enroll to other application if user (centralId) is already activated for any one application

                        String currentAppIdValue = new SetGetAppIdInStatus(otpPojo.getChannelId(), otpPojo.getApplicationId()).getAppId(digipassStatus);

                        if (currentAppIdValue == null) {
                            digipassStatus = new SetGetAppIdInStatus(otpPojo.getChannelId(), otpPojo.getApplicationId()).setAppId(digipassStatus);
                            digipassStatusRepository.save(digipassStatus);
                        }

                    } else {
                        //System.out.println(ANSI_RED+"OTP validation failed !!! " + "Status Code : " + validationStatus+ANSI_RESET);

                        throw new Exception("OTP validation failed !!! " + "Status Code:" + validationStatus);
                    }
                }else{
                    throw new Exception("Registered application ID mismatch : Got - "+otpPojo.getApplicationId()+" Found - "+setGetAppIdInStatus.getAppId(digipassStatus));
                }
            } else {
                throw new Exception("User not Activated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            globalResponse.setMessage(e.getMessage());
            throw new Exception(e.getMessage());
        }

        return globalResponse;
    }

    public void getBlobDetail(byte[] appBlob, KernelParms kParams) {
        AAL2Wrap aal2Wrap = new AAL2Wrap();
        String[] blobInfo = {
                "TOKEN_MODEL",
                "USE_COUNT",
                "LAST_TIME_USED",
                "LAST_TIME_SHIFT",
                "TIME_BASED_ALGO",
                "EVENT_BASED_ALGO",
                "PIN_SUPPORTED",
                "UNLOCK_SUPPORTED",
                "PIN_ENABLED",
                "PIN_CH_ON",
                "PIN_CH_FORCED",
                "PIN_LEN",
                "PIN_MIN_LEN",
                "PRIMARY_TOKEN_ENABLED",
                "VIRTUAL_TOKEN_SUPPORTED",
                "VIRTUAL_TOKEN_ENABLED",
                "VIRTUAL_TOKEN_TYPE",
                "VIRTUAL_TOKEN_GRACE_PERIOD",
                "VIRTUAL_TOKEN_REMAIN_USE",
                "LAST_RESPONSE_TYPE",
                "ERROR_COUNT",
                "EVENT_VALUE",
                "LAST_EVENT_VALUE",
                "SYNC_WINDOWS",
                "CODE_WORD",
                "AUTH_MODE",
                "DERIVATION_SUPPORTED",
                "OCRA_SUITE",
                "MAX_DTF_NUMBER",
                "DTF1_MIN_LEN to DTF10_MIN_LEN",
                "DTF1_MAX_LEN to DTF10_MAX_LEN",
                "DTF1_CHK to DTF10_CHK:",
                "SECURE_CHANNEL_MSG_SIG_SUPPORTED"
        };


        List<Map<String, Object>> infoList = new ArrayList<>();
        for (int i = 0; i < blobInfo.length; i++) {
            String res = aal2Wrap.AAL2GetTokenSingleInfo(appBlob, kParams, i + 1);
            //System.out.println(blobInfo[i] + " : " + res);
        }

    }
}

//checking kernal params

