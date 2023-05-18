package com.infodev.sanimaotp.services.utils;

import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.exception.ChannelNotFoundException;

public class SetGetAppIdInStatus {
    private String channelId;
    private String applicationId;

    public SetGetAppIdInStatus(String channelId, String applicationId) {
        this.channelId = channelId;
        this.applicationId = applicationId;
    }

    public DigipassStatus setAppId(DigipassStatus digipassStatus) throws ChannelNotFoundException {
        switch (channelId) {
            case "APP1_MB":
                digipassStatus.setApp1Mb(applicationId);
                break;

            case "APP2_KIOSK":
                digipassStatus.setApp2Kiosk(applicationId);
                break;
            case "APP3_ATM":
                digipassStatus.setApp3Atm(applicationId);
                break;
            case "APP4_VBV":
                digipassStatus.setApp4Vbv(applicationId);
                break;

            case "APP5_IBANK":
                digipassStatus.setApp5Ibank(applicationId);
                break;

            case "APP6_VBV_CR":
                digipassStatus.setApp6VbvCr(applicationId);
                break;

            case "APP7_KIOSKCR":
                digipassStatus.setApp7Kioskcr(applicationId);
                break;

            case "APP8_FT_SIG":
                digipassStatus.setApp8FtSig(applicationId);
                break;
            default:
                throw new ChannelNotFoundException("Channel Id not found ");

        }
        return digipassStatus;
    }
    public String getAppId(DigipassStatus digipassStatus) throws ChannelNotFoundException {
        String appIdValue=null;
        switch (channelId) {
            case "APP1_MB":
                appIdValue=digipassStatus.getApp1Mb();
                break;

            case "APP2_KIOSK":
                appIdValue=digipassStatus.getApp2Kiosk();
                break;
            case "APP3_ATM":
                appIdValue=digipassStatus.getApp3Atm();
                break;
            case "APP4_VBV":
                appIdValue=digipassStatus.getApp4Vbv();
                break;

            case "APP5_IBANK":
                appIdValue=digipassStatus.getApp5Ibank();
                break;

            case "APP6_VBV_CR":
                appIdValue=digipassStatus.getApp6VbvCr();
                break;

            case "APP7_KIOSKCR":
                appIdValue=digipassStatus.getApp7Kioskcr();
                break;

            case "APP8_FT_SIG":
                appIdValue=digipassStatus.getApp8FtSig();
                break;
            default:
                throw new ChannelNotFoundException("Channel Id not found ");

        }
        return appIdValue;
    }
}
