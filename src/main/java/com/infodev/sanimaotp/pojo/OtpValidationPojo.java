package com.infodev.sanimaotp.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class OtpValidationPojo {
    @NotNull
    @Size(min=7,max=17)
    private String channelId;
    @NotNull
    @Size(min=8,max=8,message = "Should be of 8 digits.")
    private String otp;

    @Size(min=8,max=8,message="Should be of 8 digits(CR mode) or null(RO mode)")
    private String challenge;
    @NotNull
    @Size(min=4,max=200)
    private String centralId;
    @NotNull
    @Size(min=4,max=200)
    private String applicationId;

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public OtpValidationPojo(String channelId, String otp, String challenge, String centralId, String applicationId) {
        this.channelId = channelId;
        this.otp = otp;
        this.challenge = challenge;
        this.centralId = centralId;
        this.applicationId = applicationId;
    }

    public OtpValidationPojo() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }
}
